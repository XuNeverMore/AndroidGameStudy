package com.xunevermore.androidgamestudy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuNeverMore
 * @QQ 1045530120
 * @create on 2018/1/23 0023
 * @github https://github.com/XuNeverMore
 */

public class WuziView extends View {

    public static final int CHESS_WHITE = 0;
    public static final int CHESS_BLACK = 1;


    private int padding = 10;
    private int width;
    private int height;
    private final Paint paint;
    private Path path;
    private int size;
    private int top;
    private int cellWidth;
    private int chessRadius;
    private List<Chess> chessList = new ArrayList<>();

    private Chess[][] chessBoard = new Chess[10][10];


    public WuziView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }


    private int currentChessType = CHESS_BLACK;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        //减去两边空隙就是棋盘格子的宽度
        size = width - 2 * padding;
        cellWidth = size / 10;

        //棋子半径
        chessRadius = cellWidth / 2 - 10;

        top = (height - size) / 2;

        //格子四边构成的path
        path = new Path();
        path.moveTo(padding, top);
        path.lineTo(padding + size, top);
        path.lineTo(padding + size, top + size);
        path.lineTo(padding, top + size);
        path.close();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);


        //画一个10*10的格子棋盘
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);

        //画四个边
        canvas.drawPath(path, paint);
        paint.setStrokeWidth(5);

        for (int i = 1; i < 10; i++) {
            //画竖线
            int x = padding + i * cellWidth;
            canvas.drawLine(x, top, x, top + size, paint);

            //画横线
            int y = top + i * cellWidth;
            canvas.drawLine(padding, y, padding + size, y, paint);
        }

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //画棋子
        for (Chess c : chessList) {
            paint.setColor(c.type == CHESS_BLACK ? Color.BLACK : Color.WHITE);
            canvas.drawCircle(c.x, c.y, chessRadius, paint);
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isChessEvent(event.getX(), event.getY())) {
            handleEvent(event);
            return true;
        }
        return super.onTouchEvent(event);

    }

    /**
     * 是否是落子事件，即是否在可落子范围内
     *
     * @param x,y 坐标
     * @return
     */
    private boolean isChessEvent(float x, float y) {

        int topY = top + cellWidth - chessRadius;
        int bottomY = top + size - cellWidth + chessRadius;
        int leftX = padding + cellWidth - chessRadius;
        int rightX = padding + size - cellWidth + chessRadius;
        return x >= leftX && x <= rightX && y >= topY && y <= bottomY;

    }

    /**
     * 处理屏幕触摸事件
     *
     * @param event
     */
    private void handleEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        Chess chess = getChessFromXY((int) x, (int) y);
        if (chessList.contains(chess)) {
            return;
        }
        if (listener != null) {
            listener.onChessFell(currentChessType);
        }

        chessBoard[chess.x][chess.y] = chess;
        chessList.add(chess);

        // TODO: 2018/1/23 0023 检查是否由胜利，是则回调胜利
        boolean isVictory = checkVictory(chess);

        //换棋子颜色
        currentChessType = currentChessType == CHESS_BLACK ? CHESS_WHITE : CHESS_BLACK;
        invalidate();

    }


    /**
     * 检查当盖子落下后是否游戏结束
     *
     * @param chess
     * @return
     */
    private boolean checkVictory(Chess chess) {


        int hCount = checkHorizontal(chess.type, chess.row, chess.column);


        return false;
    }



    private int checkLeftTop(int type, int row, int column){

        int result = 1;




        return result;
    }


    private int checkVertical(int type, int row, int column) {
        int result = 1;
        for (int i = row + 1; i < 10; i++) {
            Chess chess = chessBoard[i][column];
            if (chess != null && chess.type == type) {
                result++;
            } else {
                break;
            }
        }

        for (int i = row - 1; i > 0; i--) {
            Chess chess = chessBoard[i][column];
            if (chess != null && chess.type == type) {
                result++;
            } else {
                break;
            }
        }

        return result;
    }


    private int checkHorizontal(int type, int row, int column) {

        int result = 1;
        for (int i = column + 1; i < 10; i++) {
            Chess chess = chessBoard[row][i];
            if (chess != null && chess.type == type) {
                result++;
            } else {
                break;
            }
        }

        for (int i = column - 1; i > 0; i--) {
            Chess chess = chessBoard[row][i];
            if (chess != null && chess.type == type) {
                result++;
            } else {
                break;
            }
        }

        return result;
    }

    public Chess getChessFromXY(int x, int y) {
        Chess chess = new Chess();

        chess.column = getIndex(x - padding);
        chess.row = getIndex(y - top);

        chess.x = chess.column * cellWidth + padding;
        chess.y = chess.row * cellWidth + top;

        chess.type = currentChessType;

        return chess;
    }

    private int getIndex(int adjustPosition) {
        int index = adjustPosition / cellWidth;
        int offset = adjustPosition % cellWidth;
        if (offset > (float) cellWidth / 2) {
            index++;
        }
        return index;
    }


    class Chess {
        int type;
        int column;
        int row;
        int x;
        int y;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Chess) {
                return this.column == ((Chess) obj).column && this.row == ((Chess) obj).row;
            }
            return super.equals(obj);
        }
    }

    private ChessEventListener listener;

    public void setChessEventListener(ChessEventListener listener) {
        this.listener = listener;
    }

    public interface ChessEventListener {

        /**
         * 棋子落下回调
         *
         * @param chessType 棋子类型
         */
        void onChessFell(int chessType);

        /**
         * 某一方胜利
         *
         * @param chessType 棋子类型
         */
        void onVictory(int chessType);


    }


}
