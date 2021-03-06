package org.tamilscriptconverter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Arunachalam
 */
public class SpecialSoundChar implements Serializable
{
    private String keyChar;
    private String previousChar;
    private String nextChar;
    private String valueChar;

    public SpecialSoundChar()
    {
        //default constructor
    }

    public SpecialSoundChar(String keyChar, String previousChar, String nextChar, String valueChar)
    {
        this.keyChar = keyChar;
        this.previousChar = previousChar;
        this.nextChar = nextChar;
        this.valueChar = valueChar;
    }


    public String getKeyChar()
    {
        return keyChar;
    }

    public void setKeyChar(String keyChar)
    {
        this.keyChar = keyChar;
    }

    public String getPreviousChar()
    {
        return previousChar;
    }

    public void setPreviousChar(String previousChar)
    {
        this.previousChar = previousChar;
    }

    public String getNextChar()
    {
        return nextChar;
    }

    public void setNextChar(String nextChar)
    {
        this.nextChar = nextChar;
    }

    public String getValueChar()
    {
        return valueChar;
    }

    public void setValueChar(String valueChar)
    {
        this.valueChar = valueChar;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof SpecialSoundChar) {
            return EqualsBuilder.reflectionEquals(this, object);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
