package com.example.igor.smartlock;
/**
 * Created by Igor on 29.04.2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class LockView extends ViewGroup {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    Boolean CanDrawBoolean;
    private List<Pair<NodeView, NodeView>> lineList;
    private NodeView currentNode;
    private StringBuilder pwdSb;
    private CallBack callBack;
    private int lineColor;
    private Drawable nodeSrc;
    private Drawable nodeOnSrc;

    public LockView(Context context) {
        this(context, null);
    }
    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    public LockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(attrs, defStyleAttr);
        CanDrawBoolean=true;
    }


    private void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs,    R.styleable.LockView, defStyleAttr, 0);

        nodeSrc = a.getDrawable(R.styleable.LockView_nodeSrc);
        nodeOnSrc = a.getDrawable(R.styleable.LockView_nodeOnSrc);
        lineColor = Color.argb(0, 0, 0, 0);
        lineColor = a.getColor(R.styleable.LockView_lineColor, lineColor);
        float lineWidth = 20.0f;
        lineWidth = a.getDimension(R.styleable.LockView_lineWidth, lineWidth);

        a.recycle();

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineColor);
        paint.setAntiAlias(true);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.widthPixels,   Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);

        for (int n = 0; n < 9; n++) {
            NodeView node = new NodeView(getContext(), n + 1);
            addView(node);
        }
        lineList = new ArrayList<Pair<NodeView,NodeView>>();
        pwdSb = new StringBuilder();

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!changed) {
            return;
        }
        int width = right - left;
        int nodeWidth = width / 3;
        int nodePadding = nodeWidth /3;
        for (int n = 0; n < 9; n++) {
            NodeView node = (NodeView) getChildAt(n);
            int row = n / 3;
            int col = n % 3;
            int l = col * nodeWidth + nodePadding+25;
            int t = row * nodeWidth + nodePadding+25;
            int r = col * nodeWidth + nodeWidth - nodePadding-25;
            int b = row * nodeWidth + nodeWidth - nodePadding-25;
            node.layout(l, t, r, b);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // if(CanDrawBoolean)
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(CanDrawBoolean)
                {
                    NodeView nodeAt = getNodeAt(event.getX(), event.getY());

                    if (nodeAt == null && currentNode == null) {
                        return true;
                    } else {

                        clearScreenAndDrawList();

                        if (currentNode == null) {
                            currentNode = nodeAt;
                            currentNode.setHighLighted(true);
                            pwdSb.append(currentNode.getNum());
                        }
                        else if (nodeAt == null || nodeAt.isHighLighted()) {

                            canvas.drawLine(currentNode.getCenterX(), currentNode.getCenterY(), event.getX(), event.getY(), paint);
                        } else {

                            Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.zoom_point);
                            nodeAt.startAnimation(animation);

                            canvas.drawLine(currentNode.getCenterX(), currentNode.getCenterY(), nodeAt.getCenterX(), nodeAt.getCenterY(), paint);

                            //Log.e("CurNode",currentNode.getNum()+"");
                            //Log.e("NodeAT",nodeAt.getNum()+"");

                            if(currentNode.getNum()==1 && nodeAt.getNum()==3)
                            {
                                NodeView n=(NodeView) getChildAt(1);
                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }

                            if(currentNode.getNum()==4 && nodeAt.getNum()==6)
                            {
                                NodeView n=(NodeView) getChildAt(4);
                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }
                            if(currentNode.getNum()==7 && nodeAt.getNum()==9)
                            {
                                NodeView n=(NodeView) getChildAt(7);

                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }
                            if(currentNode.getNum()==1 && nodeAt.getNum()==7)
                            {
                                NodeView n=(NodeView) getChildAt(3);

                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }
                            if(currentNode.getNum()==2 && nodeAt.getNum()==8)
                            {
                                NodeView n=(NodeView) getChildAt(4);

                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }
                            if(currentNode.getNum()==3 && nodeAt.getNum()==9)
                            {
                                NodeView n=(NodeView) getChildAt(5);

                                n.startAnimation(animation);
                                n.setHighLighted(true);
                                Pair<NodeView, NodeView> p = new Pair<NodeView, NodeView>(currentNode, n);
                                lineList.add(p);
                                pwdSb.append(n.getNum());
                                currentNode=n;
                            }

                            nodeAt.setHighLighted(true);
                            Pair<NodeView, NodeView> pair = new Pair<NodeView, NodeView>(currentNode, nodeAt);
                            lineList.add(pair);
                            currentNode = nodeAt;

                            pwdSb.append(currentNode.getNum());

                        }
                        invalidate();
                    }


                }

                return true;

            case MotionEvent.ACTION_UP:
                // Log.e("Password:",pwdSb.toString()+"");

                if (pwdSb.length() < 4) {
                    pwdSb.setLength(0);
                    lineList.clear();
                    clearScreenAndDrawList();
                    currentNode = null;

                    for (int n = 0; n < getChildCount(); n++) {
                        NodeView node = (NodeView) getChildAt(n);
                        node.setHighLighted(false);
                    }

                    return super.onTouchEvent(event);
                }


                currentNode = null;


                for (int n = 0; n < getChildCount(); n++) {
                    NodeView node = (NodeView) getChildAt(n);
                    if(node.isHighLighted())
                    {
                        node.SetNodeHighlighted();

                    }
                }

                CanDrawBoolean=false;
                lineColor = Color.argb(255, 39, 191, 176);
                paint.setColor(lineColor);

                clearScreenAndDrawList();


                if (callBack != null) {
                    callBack.onFinish(pwdSb.toString());
                    pwdSb.setLength(0);

                }


                invalidate();

                return true;
        }
        return super.onTouchEvent(event);
    }
    public void Repeate()
    {
        CanDrawBoolean=true;
        pwdSb.setLength(0);
        lineList.clear();

        currentNode = null;

        for (int n = 0; n < getChildCount(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            node.setHighLighted(false);
            if(!node.isHighLighted())
            {
                node.SetNodeNotHighlighted();
            }
        }

        lineColor = Color.argb(255, 61, 255, 236);
        paint.setColor(lineColor);

        clearScreenAndDrawList();
        invalidate();


    }
    private boolean isOnLine(NodeView A,float endx,float endy,NodeView Find)
    {
        float y=( Find.getCenterY() - A.getCenterY())/(endy - A.getCenterY());
        float x=( Find.getCenterX() - A.getCenterX())/(endx - A.getCenterX());
      /*  if(Find.getNum()==5)
        Log.e("On Line","A.x "+ A.getCenterX() + " || A.y" + A.getCenterY() + " End.x " + endx + " || End.y"+endy+
                "  Centre x "+Find.getCenterX()+" || Centre y "+ Find.getCenterY());*/
        if( x==y ) return true;
        else return false;

    }
    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<NodeView, NodeView> pair : lineList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(), pair.second.getCenterX(), pair.second.getCenterY(), paint);
        }

    }
    private NodeView getNodeAt(float x, float y) {

        for (int n = 0; n < getChildCount(); n++) {
            NodeView node = (NodeView) getChildAt(n);
            if (!(x + 25 >= node.getLeft() && x - 25 < node.getRight())) {
                continue;
            }
            if (!(y + 25>= node.getTop() && y - 25< node.getBottom())) {
                continue;
            }
            return node;
        }
        return null;
    }
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


//////////////////// NodeView Class ////////////////////////////////////////////////////////////////////////////////////////////

    public class NodeView extends View {

        private int num;
        private boolean highLighted;

        private NodeView(Context context) {
            super(context);
        }

        public NodeView(Context context, int num) {
            this(context);
            this.num = num;
            highLighted = false;
            if (nodeSrc == null) {
                setBackgroundResource(0);
            } else {
                setBackgroundDrawable(nodeSrc);
            }
        }

        public boolean isHighLighted() {
            return highLighted;
        }

        public void setHighLighted(boolean highLighted) {
            this.highLighted = highLighted;
           /* if (highLighted) {
                if (nodeOnSrc == null) {
                    setBackgroundResource(0);
                } else {
                    setBackgroundDrawable(nodeOnSrc);
                }
            } else {
                if (nodeSrc == null) {
                    setBackgroundResource(0);
                } else {
                    setBackgroundDrawable(nodeSrc);
                }
            }*/
        }
        public void SetNodeHighlighted()
        {
            setBackgroundDrawable(nodeOnSrc);
        }
        public void SetNodeNotHighlighted()
        {
            setBackgroundDrawable(nodeSrc);
        }
        public int getCenterX() {
            return (getLeft() + getRight()) / 2;
        }

        public int getCenterY() {
            return (getTop() + getBottom()) / 2;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
    public interface CallBack {
        public void onFinish(String password);
    }
}
