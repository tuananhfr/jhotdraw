/*
 * @(#)SelectionOpacityIcon.java  1.0  2008-05-23
 *
 * Copyright (c) 2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import java.net.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;

/**
 * SelectionOpacityIcon draws a shape with the specified fillColor for the selected
 * figures in the current drawing view.
 * If now figures are selcted, the specified fillColor is taken from the DrawingEditor.
 * <p>
 * The behavior for choosing the drawn fillColor matches with
 * {@link SelectionColorChooserAction }.
 * 
 * @author Werner Randelshofer
 * @version 1.0 2008-05-23 Created.
 */
public class SelectionOpacityIcon extends javax.swing.ImageIcon {

    private DrawingEditor editor;
    private AttributeKey<Double> opacityKey;
    private AttributeKey<Color> fillColorKey;
    private AttributeKey<Color> strokeColorKey;
    private Shape fillShape;
    private Shape strokeShape;

    /** Creates a new instance.
     * @param editor The drawing editor.
     * @param opacityKey The opacityKey of the default attribute
     * @param imageLocation the icon image
     * @param fillShape The shape to be drawn with the fillColor of the default
     * attribute.
     */
    public SelectionOpacityIcon(
            DrawingEditor editor,
            AttributeKey<Double> opacityKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            URL imageLocation,
            Shape fillShape,
            Shape strokeShape) {
        super(imageLocation);
        this.editor = editor;
        this.opacityKey = opacityKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    public SelectionOpacityIcon(
            DrawingEditor editor,
            AttributeKey<Double> opacityKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            Image image,
            Shape fillShape,
            Shape strokeShape) {
        super(image);
        this.editor = editor;
        this.opacityKey = opacityKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        super.paintIcon(c, g, x, y);
        Double opacity;
        Color fillColor;
        Color strokeColor;
            DrawingView view = editor.getActiveView();
            if (view != null && view.getSelectedFigures().size() == 1) {
                opacity = opacityKey.get(view.getSelectedFigures().iterator().next());
                fillColor = (fillColorKey == null) ? null : fillColorKey.get(view.getSelectedFigures().iterator().next());
                strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(view.getSelectedFigures().iterator().next());
            } else {
                opacity = opacityKey.get(editor.getDefaultAttributes());
                fillColor = (fillColorKey == null) ? null : fillColorKey.get(editor.getDefaultAttributes());
                strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(editor.getDefaultAttributes());
            }

        if (fillColorKey != null && fillShape != null) {
            if (opacity != null) {
                if (fillColor == null) {
                    fillColor = Color.BLACK;
                }
                g.setColor(new Color((((int) (opacity * 255)) << 24) | (fillColor.getRGB() & 0xffffff), true));
                g.translate(x, y);
                g.fill(fillShape);
                g.translate(-x, -y);
            }
            }
        if (strokeColorKey != null && strokeShape != null) {
            if (opacity != null) {
                if (strokeColor == null) {
                    strokeColor = Color.BLACK;
                }
                g.setColor(new Color((((int) (opacity * 255)) << 24) | (strokeColor.getRGB() & 0xffffff), true));
                g.translate(x, y);
                g.draw(strokeShape);
                g.translate(-x, -y);
            }
        }
    }
}