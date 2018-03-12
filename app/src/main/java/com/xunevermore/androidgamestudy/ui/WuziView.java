package com.xunevermore.androidgamestudy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import com.xunevermore.androidgamestudy.R;

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
    private Bitmap blackChess;
    private Bitmap whiteChess;

    private int blackCount = 0;
    private int whiteCount = 0;

    private int STATE_GAMING = 0;
    private int STATE_OVER = 1;

    private int state = STATE_GAMING;
    private Rect rectFun;
    private int lineHeight;


    public WuziView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);

        blackChess = BitmapFactory.decodeResource(getResources(), R.mipmap.black);
        whiteChess = BitmapFactory.decodeResource(getResources(), R.mipmap.white);

//        src.set(0, 0, blackChess.getWidth(), blackChess.getHeight());
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
        chessRadius = cellWidth / 2 - 5;

        top = 3 * (height - size) / 4;

        //格子四边构成的path
        path = new Path();
        path.moveTo(padding, top);
        path.lineTo(padding + size, top);
        path.lineTo(padding + size, top + size);
        path.lineTo(padding, top + size);
        path.close();

        rectFun = new Rect(padding, padding, width - padding, top - 2 * padding);
        lineHeight = rectFun.bottom - top / 2;

        paint.setTextSize(lineHeight / 4);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setStyle(Paint.Style.STROKE);
        //画一个10*10的格子棋盘
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);

        //画四个边
        canvas.drawRect(rectFun, paint);
        canvas.drawPath(path, paint);


        paint.setStyle(Paint.Style.FILL);

        int radius = lineHeight / 4;


        paint.setColor(Color.WHITE);
        canvas.drawCircle(rectFun.left + 3 * rectFun.width() / 4, rectFun.top + rectFun.height() / 4, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(rectFun.left + rectFun.width() / 4, rectFun.top + rectFun.height() / 4, radius, paint);

        //黑色方胜利场数
        paint.setColor(Color.WHITE);
        String blackString = String.valueOf(blackCount);
        paint.getTextBounds(blackString, 0, blackString.length(), src);
        int height = src.height();
        int textY = rectFun.top + rectFun.height() / 4 + height / 2;
        int wX = (int) (padding + rectFun.width() / 4 - paint.measureText(blackString) / 2);
        canvas.drawText(blackString, wX, textY, paint);

        //白色方胜利场数
        paint.setColor(Color.BLACK);
        String whiteString = String.valueOf(whiteCount);
        paint.getTextBounds(blackString, 0, whiteString.length(), src);
        int bX = (int) (padding + 3 * rectFun.width() / 4 - paint.measureText(whiteString) / 2);
        canvas.drawText(whiteString, bX, textY, paint);

        paint.setStrokeWidth(5);

        if (state == STATE_OVER && victoryOne != -1) {
            String victoryDes = victoryOne == CHESS_BLACK ? "黑方胜利" : "白方胜利";
            paint.getTextBounds(victoryDes, 0, victoryDes.length(), src);
            int vY = rectFun.top + 3 * rectFun.height() / 4 + src.height() / 2;

            int vX = (int) (padding + rectFun.width() / 4 - paint.measureText(victoryDes) / 2);
            canvas.drawText(victoryDes, vX, vY, paint);


            String again = "再来一局";
            paint.getTextBounds(again, 0, victoryDes.length(), src);


            int aY = rectFun.top + 3 * rectFun.height() / 4 + src.height() / 2;
            float textWidth = paint.measureText(again);
            int aX = (int) (padding + 3 * rectFun.width() / 4 - textWidth / 2);

            int top = 3 * rectFun.height() / 4 - src.height() / 2;
            dst.set(aX, top, (int) (aX + textWidth), (int) (top + src.height()+paint.getFontMetrics().descent));

            canvas.drawRect(dst,paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(again, aX, vY, paint);
        }
        paint.setColor(Color.BLACK);


        for (int i = 1; i < 10; i++) {
            //画竖线
            int x = padding + i * cellWidth;
            canvas.drawLine(x, top, x, top + size, paint);

            //画横线
            int y = top + i * cellWidth;
            canvas.drawLine(padding, y, padding + size, y, paint);
        }

        //画棋子
        for (Chess c : chessList) {
            paint.setColor(c.type == CHESS_BLACK ? Color.BLACK : Color.WHITE);
            canvas.drawCircle(c.x, c.y, chessRadius, paint);

//            dst.set(c.x-chessRadius,c.y-chessRadius,c.x+chessRadius,c.y+chessRadius);

//            canvas.drawBitmap(
//                    c.type == CHESS_BLACK?blackChess:whiteChess,
//                    src,
//                    dst,
//                    paint);
        }

    }

    private Rect src = new Rect();
    private Rect dst = new Rect();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (state == STATE_OVER && dst.contains((int) event.getX(), (int) event.getY())) {
            //再来一局
            reset();
            return true;
        }
        if (state == STATE_GAMING && isChessEvent(event.getX(), event.getY())) {

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

        chessBoard[chess.row][chess.column] = chess;
        chessList.add(chess);


        //  2018/1/23 0023 检查是否由胜利，是则回调胜利

        boolean isVictory = checkVictory(chess);
        if (isVictory) {
            if (listener != null) {
                listener.onVictory(chess.type);
            }
            state = STATE_OVER;
            if (currentChessType == CHESS_BLACK) {//黑子胜利
                blackCount++;
                victoryOne = CHESS_BLACK;

            } else {//白子胜利
                whiteCount++;
                victoryOne = CHESS_WHITE;
            }

        }

        //换棋子颜色
        swapChessType();
        invalidate();

    }

    int victoryOne = -1;//胜利方

    private void swapChessType() {
        currentChessType = currentChessType == CHESS_BLACK ? CHESS_WHITE : CHESS_BLACK;
    }


    /**
     * 检查当盖子落下后是否游戏结束
     *
     * @param chess
     * @return
     */
    private boolean checkVictory(Chess chess) {


        int hCount = checkHorizontal(chess.type, chess.row, chess.column);

        int vCount = checkVertical(chess.type, chess.row, chess.column);

        int c24 = checkLT2RB(chess.type, chess.row, chess.column);

        int c13 = checkRT2LB(chess.type, chess.row, chess.column);

        return hCount >= 5 || vCount >= 5 || c24 >= 5 || c13 >= 5;
    }

    private int checkRT2LB(int type, int row, int column) {
        int result = 1;

        //右上
        for (int i = 1; i < Math.min(row, 10 - column); i++) {

            Chess c = chessBoard[row - i][column + i];
            if (c != null && c.type == type) {
                result++;
            } else {
                break;
            }

        }
        //左下
        for (int i = 1; i < Math.min(column, 10 - row); i++) {
            Chess c = chessBoard[row + i][column - i];
            if (c != null && c.type == type) {
                result++;
            } else {
                break;
            }
        }


        return result;
    }


    private int checkLT2RB(int type, int row, int column) {

        int result = 1;
        //左上

        for (int i = 1; i < Math.min(row, column); i++) {
            Chess c = chessBoard[row - i][column - i];
            if (c != null && c.type == type) {
                result++;
            } else {
                break;
            }

        }

        //右下
        for (int i = 1; i < Math.min(10 - row, 10 - column); i++) {

            Chess c = chessBoard[row + i][column + i];
            if (c != null && c.type == type) {
                result++;
            } else {
                break;
            }

        }


        return result;
    }


    /**
     * 检查数值方向的最大连子数目
     *
     * @param type
     * @param row
     * @param column
     * @return
     */
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


    /**
     * 检查水平方向最大的连子数目
     *
     * @param type
     * @param row
     * @param column
     * @return
     */
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


    /**
     * 重新开始一局
     */
    public void reset() {
        state = STATE_GAMING;
        chessList.clear();
        chessBoard = new Chess[10][10];
        swapChessType();
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        blackChess.recycle();
        blackChess = null;
        whiteChess.recycle();
        whiteChess = null;
        chessList.clear();
        chessList = null;
        chessBoard = null;

    }
}
