package br.com.fgr.emergencia.ui.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import br.com.fgr.emergencia.models.general.Coordenada;
import java.util.ArrayList;
import java.util.List;

public class RadarView extends View implements Runnable {

    private List<Coordenada> coordenadas = new ArrayList<>();
    private double angle = 0.00;

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint paint = new Paint();
        Paint paintLine = new Paint();
        Paint paintPoint = new Paint();
        Paint pontoAcionado = new Paint();
        int centerY = (getTop() + getBottom()) / 2;
        int centerX = (getLeft() + getRight()) / 2;
        int radius = (int) ((centerX - getLeft()) * 0.9);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paintPoint.setColor(Color.GREEN);
        paintLine.setColor(Color.GREEN);
        paintLine.setStrokeWidth(1);
        pontoAcionado.setColor(Color.WHITE);

        int endPointX = centerX + (int) (radius * Math.cos(angle));
        int endPointY = centerY + (int) (radius * Math.sin(angle));

        canvas.drawCircle(centerX, centerY, 10, paintPoint);
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawLine(centerX, centerY, endPointX, endPointY, paintLine);

        for (Coordenada c : coordenadas) {

            int novoX = (int) (centerX + radius * c.getLat());
            int novoY = (int) (centerY + radius * c.getLgn());

            if (verificarAlinhamento(centerX, centerY, endPointX, endPointY, novoX, novoY)) {
                canvas.drawCircle(novoX, novoY, 20, pontoAcionado);
            } else {
                canvas.drawCircle(novoX, novoY, 10, paintPoint);
            }
        }

        postDelayed(this, 1);
    }

    @Override public void run() {

        angle += 0.01;

        if (angle == 6.28) angle = 0.00;

        invalidate();
    }

    public void setList(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }

    private boolean verificarAlinhamento(int x1, int y1, int x2, int y2, int x, int y) {

        int resultado = x * (y1 - y2) + y * (x2 - x1) + x1 * y2 - y1 * x2;

        return resultado <= 750 && resultado >= -750 &&
            (((x >= x1) && (x <= x2)) || ((x >= x2) && (x <= x1))) && (((y >= y1) && (y <= y2)) || (
            (y >= y2)
                && (y <= y1)));
    }
}