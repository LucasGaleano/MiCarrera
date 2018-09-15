package com.example.lucasgaleano.micarrera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.example.lucasgaleano.micarrera.R;
import java.util.List;

public class SubjectTreeView extends ViewGroup {

    private final String TAG = "Treeview";
    Paint mPaint;
    private Context context;
    private int mWidth;
    private int mHeight;
    private int mHeightPosition;
    private int positions = 1;
    private int spaceBetweenNodes = 32;
    private int maxWidthNode = 0;
    private int colorHabilitada;
    private int colorInhabilitada;
    private int colorCursada;
    private int colorCursando;
    private int colorAprobada;


    private OnClickListener onClick;

    public SubjectTreeView(Context context) {
        super(context, null, 0);
        this.context = context;
    }

    public SubjectTreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        init(attrs);
    }

    public SubjectTreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);

    }

    private void init(@Nullable AttributeSet set) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        colorHabilitada = getResources().getColor(R.color.colorHabilitada);
        colorInhabilitada = getResources().getColor(R.color.colorInhabilitada);
        colorCursada = getResources().getColor(R.color.colorCursada);
        colorCursando = getResources().getColor(R.color.colorCursando);
        colorAprobada = getResources().getColor(R.color.colorAprobada);

        mPaint.setColor(colorInhabilitada);



        if (set == null) {
            return;
        }


    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        positionNodesTree();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mCountChild = getChildCount();
        for (int index = 0; index < mCountChild; index++) {
            Log.d(TAG,"on measure childs: " + String.valueOf(mCountChild));
            View mChildView = getChildAt(index);
            measureChild(mChildView, widthMeasureSpec, heightMeasureSpec);
            Log.d("child width: ", String.valueOf(mChildView.getMeasuredWidth()));
            Log.d("child height: ", String.valueOf(mChildView.getMeasuredHeight()));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mHeightPosition = h / positions;
    }

    public void addNode(String name, int level, int position, int state, List<String> predecessors) {

        this.positions = Math.max(this.positions, position);
        SubjectView aux = new SubjectView(context, name, level, position, predecessors);

        updateStatusNode(aux,state,predecessors);
        this.addView(aux);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


        int mCountChild = getChildCount();
        Log.d(TAG,"on dispatch draw childs: " + String.valueOf(mCountChild));

        for (int index = 0; index < mCountChild; index++) {
            SubjectView subject = (SubjectView) getChildAt(index);
            drawEdgeToPredeccessors(canvas, subject);
        }
        Log.d("Node: ", "------------------------------");
    }

    private void drawEdgeToPredeccessors(Canvas canvas, SubjectView subject) {

        for (String predecessorName : subject.getPredecessor()) {

            SubjectView predecessorView = getSubjectViewFromName(predecessorName);
            if (predecessorView == null) continue;
            drawCurveViewToView(canvas, predecessorView, subject);

        }

    }

    private void drawCurveViewToView(Canvas canvas, SubjectView predecessorView, SubjectView subject) {

        int xTo = subject.getLeft();
        int yTo = subject.getTop() + (subject.getMeasuredHeight() / 2);
        Path path = new Path();

        mPaint.setColor(getColorFromStateSubject(predecessorView));
        int xFrom = predecessorView.getRight();
        int yFrom = predecessorView.getTop() + (predecessorView.getMeasuredHeight() / 2);
        int distanceXBeetwenNodes = xFrom - xTo;
        //int distanceYBeetwenNodes = yFrom - yTo;
        path.moveTo(xTo, yTo);
        path.cubicTo(xTo + distanceXBeetwenNodes / 2, yTo, xTo + distanceXBeetwenNodes / 2, yFrom, xFrom, yFrom);
        Log.d("node: ", subject.getText().toString() + " " + predecessorView.getText().toString());
        canvas.drawPath(path, mPaint);

    }

    private int getColorFromStateSubject(SubjectView predecessorView) {

        if (predecessorView.getState() == getResources().getInteger(R.integer.HABILITADA))
            return colorHabilitada;

        if (predecessorView.getState() == getResources().getInteger(R.integer.INHABILITADA))
            return colorInhabilitada;

        if (predecessorView.getState() == getResources().getInteger(R.integer.CURSANDO))
            return colorCursando;

        if (predecessorView.getState() == getResources().getInteger(R.integer.CURSADA))
            return colorCursada;

        if (predecessorView.getState() == getResources().getInteger(R.integer.APROBADA))
            return colorAprobada;

        Log.d(TAG,"se eligio color por defecto");
        return colorInhabilitada;
    }

    private SubjectView getSubjectViewFromName(String subjectName) {

        int mCountChild = getChildCount();
        for (int index = 0; index < mCountChild; index++) {
            SubjectView subject = (SubjectView) getChildAt(index);
            if (subjectName.equals(subject.getText()))
                return subject;
        }
        return null;

    }

    private void positionNodesTree() {

        mHeightPosition = mHeight / positions;
        int mCountChild = getChildCount();
        maxWidthNode = maxWidthNode();

        for (int index = 0; index < mCountChild; index++) {
            SubjectView mChildView = (SubjectView) getChildAt(index);

            int position = mHeightPosition * mChildView.getPosition();
            position = position - (mHeightPosition - mChildView.getMeasuredHeight() / 2);
            int childH = mChildView.getMeasuredHeight();
            int childW = mChildView.getMeasuredWidth();
            int childStartPos = (maxWidthNode + spaceBetweenNodes) * (mChildView.getLevel() - 1);
            int childEndPos = childStartPos + childW;
            int childTopPos = position - (childH / 2);
            int childBottomPos = position + (childH / 2);

            Log.d("Pchild L,T,R,B: ", String.valueOf(childStartPos) + " " + String.valueOf(childTopPos) + " " +
                    String.valueOf(childEndPos) + " " + String.valueOf(childBottomPos));

            mChildView.layout(childStartPos+16, childTopPos+16, childEndPos+16, childBottomPos+16);

        }

    }


    private int maxWidthNode() {

        int mCountChild = getChildCount();


        for (int index = 0; index < mCountChild; index++) {
            SubjectView SubjectView = (SubjectView) getChildAt(index);

            if (SubjectView.getMeasuredWidth() > maxWidthNode)
                maxWidthNode = SubjectView.getMeasuredWidth();
        }
        return maxWidthNode;

    }


    public void setOnClicks(OnClickListener onClicks) {
        this.onClick = onClicks;
    }


    public void doInvalidate() {
        Log.d(TAG,"invalidate");
        invalidate();
    }

    public void updateNode(String name, int level, int position, int state, List<String> predecessors) {


        SubjectView sub = getSubjectViewFromName(name);

        if (sub != null) { // node exist
            updateStatusNode(sub,state,predecessors);
        } else { //node not exist
            this.addNode(name, level, position, state, predecessors);
        }

    }

    private void updateStatusNode(SubjectView sub, int state, List<String> predecessors){
        if (state == getResources().getInteger(R.integer.INHABILITADA) && allPredecessorDone(predecessors)) {
            state = getResources().getInteger(R.integer.HABILITADA);
            sub.setOnClickListener(this.onClick);
        }
        sub.setState(state);
    }

    private boolean allPredecessorDone(List<String> predecessors) {

        for(String pre:predecessors){
            SubjectView sub = getSubjectViewFromName(pre);
            if(sub==null)continue;
            if(sub.getState() == getResources().getInteger(R.integer.INHABILITADA)
                    || sub.getState() == getResources().getInteger(R.integer.CURSANDO)
                    || sub.getState() == getResources().getInteger(R.integer.HABILITADA))
                return false;
        }
        return true;
    }
}
