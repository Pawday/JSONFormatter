package per.pawday.jsonFormatter;

import per.pawday.jsonFormatter.constants.IndentChars;
import per.pawday.jsonFormatter.constants.IndentsStyles;
import per.pawday.jsonFormatter.exceptions.JsonFormatterException;

import java.io.IOException;
import java.io.StringReader;

public class JsonFormatter
{
    public final int indentStyle;
    public final int indentCount;
    public final char indentChar;

    private final String newLine = System.lineSeparator();

    public JsonFormatter(int indentStyle, char indentChar, int indentCount) throws JsonFormatterException
    {
        if (indentCount < 0)
        {
            throw new JsonFormatterException("The number of indents can't be less than zero.");
        }
        if (indentChar != IndentChars.SPACE && indentChar != IndentChars.TAB)
        {
            throw new JsonFormatterException("Your indent symbol cannot be used.");
        }
        this.indentChar = indentChar;
        this.indentStyle = indentStyle;
        this.indentCount = indentCount;
    }

    public String formattToString(String string)
    {
        StringReader reader = new StringReader(string);

        boolean inString = false;
        int currentIndent = 0;

        int by;

        StringBuilder builder = new StringBuilder();

        try
        {
            short firstCharController = 0;
            while ((by = reader.read()) != -1)
            {
                char c = (char) by;

                if (! inString)
                {

                    switch (c)
                    {
                        case '{':
                        case '[':
                            if (this.indentStyle == IndentsStyles.ONE_TRUE_BRACING_STYLE)
                            {
                                if( firstCharController == 0)
                                {
                                    builder.append(c).append(newLine);
                                } else builder.append(c).append(newLine);
                                currentIndent++;
                                for (int i = 0; i < currentIndent; i++)
                                {
                                    builder.append(indentChar);
                                }
                            }
                            else if (this.indentStyle == IndentsStyles.ERIC_ALLMANS_STYLE)
                            {
                                if( firstCharController != 0)
                                {
                                    builder.append(newLine);

                                    for (int i = 0; i < currentIndent; i++)
                                    {
                                        builder.append(indentChar);
                                    }
                                }
                                builder.append(c).append(System.lineSeparator());

                                currentIndent++;

                                for (int i = 0; i < currentIndent; i++)
                                {
                                    builder.append(indentChar);
                                }
                            }
                        break;

                        case '}':
                        case ']':


                            builder.append(newLine);
                            currentIndent--;

                            for (int i = 0; i < currentIndent; i++)
                            {
                                builder.append(indentChar);
                            }
                            builder.append(c);
                        break;


                        case '"':
                            builder.append('"');
                            inString = true;
                        break;


                        case ':':
                            builder.append(' ').append(':').append(' ');
                        break;

                        case ',':
                            builder.append(',').append(newLine);
                            for (int i = 0; i < currentIndent; i++)
                            {
                                builder.append(indentChar);
                            }
                        break;

                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '.':
                            builder.append(c);
                        break;


                    }
                }
                else
                {
                    switch (c)
                    {
                        case '"':
                            builder.append('"');
                            inString = false;
                        break;

                        default:
                            builder.append(c);
                    }
                }

                if(firstCharController == 0)
                {
                    firstCharController++;
                }
            }
        }
        catch (IOException ignored)
        {
            /*
            * I don't understand how it can throw IOException from the prepared line.
            */
        }

        return builder.toString();
    }







}
