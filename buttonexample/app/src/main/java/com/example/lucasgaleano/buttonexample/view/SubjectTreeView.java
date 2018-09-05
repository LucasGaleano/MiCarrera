package com.example.lucasgaleano.buttonexample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.lucasgaleano.buttonexample.R;
import com.example.lucasgaleano.buttonexample.database.Subject;

import java.util.List;

public class SubjectTreeView extends ViewGroup {


    Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mHeightPosition;
    private int positions = 1;
    private int spaceBetweenNodes = 32;
    private int maxWidthNode = 0;
    private int colorHabilitada;
    private int colorInhabilitada;
    private int colorCursada;

    public SubjectTreeView(Context context) {
        super(context,null,0);
    }

    public SubjectTreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        init(attrs);
    }

    public SubjectTreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(@Nullable AttributeSet set){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        colorHabilitada = getResources().getColor(R.color.colorHabilitada);
        colorInhabilitada = getResources().getColor(R.color.colorInhabilitada);
        colorCursada = getResources().getColor(R.color.colorCursada);

        mPaint.setColor(colorInhabilitada);

        if(set == null){
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
            View mChildView = getChildAt(index);
            measureChild(mChildView, widthMeasureSpec, heightMeasureSpec);
            Log.d("child width: ", String.valueOf(mChildView.getMeasuredWidth()));
            Log.d("child height: ", String.valueOf(mChildView.getMeasuredHeight()));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Calculate the radius from the width and height.
        mWidth = w;
        mHeight = h;
        mHeightPosition = h / positions ;
    }

    public void addNode(String name, int level, int position, int state, List<String> predecessors) {

        this.positions = Math.max(this.positions,position);
        SubjectView aux = new SubjectView( getContext(),name,level, position, predecessors);
        aux.setState(state);
        this.addView(aux);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int mCountChild = getChildCount();



        for (int index = 0; index < mCountChild; index++) {
            SubjectView subject = (SubjectView) getChildAt(index);
            drawEdgeToPredeccessors(canvas, subject);
        }
        Log.d("Node: ","------------------------------");
    }

    private void drawEdgeToPredeccessors(Canvas canvas, SubjectView subject) {

        for (String predecessorName : subject.getPredecessor()) {

           SubjectView predecessorView = getSubjectViewFromName(predecessorName);
            if (predecessorView == null) continue;
            drawCurveViewToView(canvas,predecessorView,subject);

        }

    }

    private void drawCurveViewToView(Canvas canvas, SubjectView predecessorView, SubjectView subject){

        int xTo = subject.getLeft();
        int yTo = subject.getTop() + (subject.getMeasuredHeight() / 2);
        Path path = new Path();

        mPaint.setColor(getColorFromStateSubject(predecessorView));
        int xFrom = predecessorView.getRight();
        int yFrom = predecessorView.getTop() + (predecessorView.getMeasuredHeight() / 2);
        int distanceXBeetwenNodes = xFrom - xTo;
        int distanceYBeetwenNodes = yFrom - yTo;
        path.moveTo(xTo , yTo );
        path.cubicTo(xTo + distanceXBeetwenNodes/2, yTo, xTo + distanceXBeetwenNodes / 2, yFrom, xFrom, yFrom);
        if(mPaint.getColor() == getResources().getColor(R.color.colorHabilitada))
            Log.d("Node: ", predecessorView.getText().toString() +" "+ subject.getText().toString());
        Log.d("node: ", subject.getText().toString() +" "+ predecessorView.getText().toString());
        canvas.drawPath(path, mPaint);

    }

    private int getColorFromStateSubject(SubjectView predecessorView) {

        if(predecessorView.getState() == SubjectView.HABILITADA)
           return colorHabilitada;

        if(predecessorView.getState() == SubjectView.INHABILIDATADA)
            return colorInhabilitada;

        if(predecessorView.getState() == SubjectView.CURSADA)
            return colorCursada;

        return colorCursada; //TODO cambiar a color por defecto
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

        int mCountChild = getChildCount();
        maxWidthNode = maxWidthNode();

        for (int index = 0; index < mCountChild; index++) {
            SubjectView mChildView = (SubjectView) getChildAt(index);

            int position = mHeightPosition * mChildView.getPosition();
            position = position - (mHeightPosition-mChildView.getMeasuredHeight()/2);
            int childH = mChildView.getMeasuredHeight();
            int childW = mChildView.getMeasuredWidth();
            int childStartPos = (maxWidthNode + spaceBetweenNodes) * (mChildView.getLevel() - 1);
            int childEndPos = childStartPos + childW;
            int childTopPos = position - (childH / 2);
            int childBottomPos = position + (childH / 2);

            Log.d("Pchild L,T,R,B: ", String.valueOf(childStartPos) + " " + String.valueOf(childTopPos) + " " +
                    String.valueOf(childEndPos) + " " + String.valueOf(childBottomPos));

            mChildView.layout(childStartPos, childTopPos, childEndPos, childBottomPos);

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

        int mCountChild = getChildCount();
        for (int index = 0; index < mCountChild; index++) {
            SubjectView subject = (SubjectView) getChildAt(index);
            subject.setOnClickListener(onClicks);
        }
    }


    public void doInvalidate() {
        invalidate();
    }

    public void updateNode(String name, int state) {

        int mCountChild = getChildCount();
        for (int index = 0; index < mCountChild; index++) {
            SubjectView sub = getSubjectViewFromName(name);
            if (name.equals(sub.getText())){
                sub.setState(state);
                continue;
            }

        }


    }
}
