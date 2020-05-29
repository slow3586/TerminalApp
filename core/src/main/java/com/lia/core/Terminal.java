package com.lia.core;

import com.lia.renderer.Mouse;
import com.lia.renderer.Quad;
import com.lia.renderer.V2i;
import com.lia.renderer.V4;

import java.util.ArrayList;

public class Terminal {

    static int lineOffset=0;
    static int optionOffset=0;
    static int linesMax =17;
    static int optionMax=Quad.countY- linesMax;
    static int linesScrollMax=0;
    static int optionsScrollMax=0;
    static boolean linesScroll = false;
    static boolean optionsScroll = false;
    static UIOption optionSelected=null;
    static ArrayList<UILine> lines = new ArrayList<>();
    static ArrayList<UIOption> options = new ArrayList<>();

    public static class UILine{
        ArrayList<String> text;
        int line;
    }

    public static class UIOption{
        ArrayList<String> text;
        Runnable runnable;
        int line;
    }

    public static String connectText(ArrayList<String> txt){
        StringBuilder out = new StringBuilder();
        for (String s : txt){
            out.append(s);
        }
        return out.toString();
    }

    public static ArrayList<String> splitText(String txt){
        ArrayList<String> l = new ArrayList<>();
        int c1 = 0;
        int c0 = 0;
        while(c1<txt.length()){
            c1+=Quad.countX-1;
            if(c1>txt.length()) {
                c1=txt.length();
            }

            l.add(txt.substring(c0,c1));
            c0=c1;
        }
        return l;
    }

    public static void addLine(String txt){
        UILine line = new UILine();
        line.text = splitText(txt);
        int l=0;
        for(UILine o0 : lines){
            l+=o0.text.size();
        }
        line.line = l;
        lines.add(line);
    }

    public static void addOption(String txt, Runnable run){
        UIOption o = new UIOption();
        o.text = splitText(txt);
        o.runnable = run;
        int l=0;
        for(UIOption o0 : options){
            l+=o0.text.size();
        }
        o.line = l;
        options.add(o);
    }

    public static void update(){
        if(Mouse.down.contains(Mouse.LEFT) || Mouse.pressed.contains(Mouse.LEFT)){
            int mouseLine = Math.round(Mouse.pos.y/Quad.sizeY);
            int optLine = mouseLine - linesMax;
            //mouse is over an option, select one
            if(optLine>=0) {
                UIOption optSel = null;
                //find actual option that is selected
                for(UIOption o : options){
                    if(optLine >= o.line && optLine < o.line+o.text.size()){
                        optSel = o;
                        break;
                    }
                }

                //set it as selected
                if (optSel !=null && optSel != optionSelected) {
                    //color out previously selected option
                    if(optionSelected!=null)
                        colorTextBack(linesMax+optionSelected.line, connectText(optionSelected.text), new V4(0, 0, 0, 1));

                    //color in new
                    optionSelected = optSel;
                    colorTextBack(linesMax+optionSelected.line, connectText(optionSelected.text), new V4(0.2f, 0.2f, 0.2f, 1));
                }
            //no option is actually selected
            } else {
                //color out last selected option
                if (optionSelected !=null)
                    colorTextBack(linesMax+optionSelected.line, connectText(optionSelected.text), new V4(0, 0, 0, 1));
                optionSelected = null;
            }
        }

        if(Mouse.released.contains(Mouse.LEFT)){
            if(optionSelected!=null){
                runOption(optionSelected);
                colorTextBack(linesMax+optionSelected.line, connectText(optionSelected.text), new V4(0, 0, 0, 1));
                optionSelected = null;
            }
        }
    }

    private static ArrayList<V2i> getTextQuads(int line, String txt){
        int curX = 0;
        int curLine = line;

        ArrayList<V2i> quads = new ArrayList<>();
        if (line < 0 || line > Quad.countY)
            return quads;
        curX = 0;

        for (int letterX = 0; letterX < txt.length(); letterX++) {
            if(curX >= Quad.countX-1){
                curX=0;
                curLine++;
            }
            if (curLine >= Quad.countY)
                return quads;

            quads.add(new V2i(curX, curLine));
            curX++;
        }

        curLine++;

        return quads;
    }

    private static int getTextLines(String txt){
        return (int) Math.ceil(1.0f*txt.length()/(Quad.countX-1));
    }

    private static void colorTextBack(int line, String txt, V4 color){
        ArrayList<V2i> quads = getTextQuads(line, txt);

        for(V2i q : quads){
            Quad.setBackColor(q.x, q.y, color.x, color.y, color.z);
        }
    }

    private static void writeText(int line, String txt){
        ArrayList<V2i> quads = getTextQuads(line, txt);

        int x=0;
        for(V2i q : quads){
            Quad.setText(q.x, q.y, txt.substring(x,x+1));
            x++;
        }
    }

    public static void clear(){
        lines.clear();
        options.clear();
    }

    public static void runOption(UIOption opt){
        opt.runnable.run();
        render();
    }

    public static void render(){
        int curLine = lineOffset;
        int curLineScr = 0;
        int line1 = 0;
        for(UILine line : lines){
            line1 = curLine - line.line;
            while(line1<line.text.size() && line1>=line.line-curLineScr) {
                if(curLineScr>linesMax)
                    break;
                writeText(curLineScr, line.text.get(line1));
                curLine++;
                curLineScr++;
                line1 = curLine - line.line;
            }
        }

        curLine = 0;
        curLineScr=linesMax;
        for(UIOption line : options){
            line1 = curLine - line.line;
            while(line1<line.text.size() && line1>=line.line-curLineScr) {
                if(curLineScr>=Quad.countY)
                    break;
                writeText(curLineScr, line.text.get(line1));
                curLine++;
                curLineScr++;
                line1 = curLine - line.line;
            }
        }

        Quad.setText(Quad.countX-1, 0,"^");
            int s0 = linesMax - 1;
            int c0 = 0;
            for (UILine l0 : lines){
                c0+=l0.text.size();
            }
            int s1 = (linesMax - 1) / c0;
            s1 = Math.min(s1, 1);

        Quad.setText(Quad.countX-1,linesMax-1,"v");


        Quad.setText(Quad.countX-1,linesMax,"^");
        Quad.setText(Quad.countX-1,Quad.countY-1,"v");
    }

}
