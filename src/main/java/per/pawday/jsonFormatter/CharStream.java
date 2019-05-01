package per.pawday.jsonFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

class CharStream extends InputStream
{

    private int pos = 0;
    private boolean isEnded = false;
    private ArrayList<Character> charsets = new ArrayList<Character>();


    @Override
    public int read() throws IOException
    {
        if (!isEnded)
        {
            if (pos < charsets.size())
            {
                pos++;
                return charsets.get(pos - 1);
            }
            else
            {
                while (pos >= charsets.size())
                {

                }
                pos++;
                return charsets.get(pos - 1);
            }
        }
        else
        {
            if (pos < charsets.size())
            {
                pos++;
                return charsets.get(pos - 1);
            }
            else return -1;
        }
    }

    public boolean complete()
    {
        if (! this.isEnded)
        {
            this.isEnded = true;
            return true;
        } else return false;
    }

    public void add(char[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            charsets.add(arr[i]);
        }
    }
    public void add(char c)
    {
        charsets.add(c);
    }

    public void add(String s)
    {
        StringReader reader = new StringReader(s);

        int b = 0;
        while (true)
        {
            try
            {
                if ((b = reader.read()) == - 1) break;
            }
            catch (IOException ignored)
            {
                /*
                 * I don't understand how it can throw IOException from the prepared line.
                 */
            }
            charsets.add((char) b);
        }

    }

}
