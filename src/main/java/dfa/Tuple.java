package dfa;

import java.util.Objects;

public class Tuple {
    private final State s1;
    private final State s2;
    public final String strFormat;
    private final String strFormatReverse;

    public Tuple(State s1, State s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.strFormat = "{" + s1.getName() + s2.getName() + "}";
        this.strFormatReverse = "{" + s2.getName() + s1.getName() + "}";
    }

    public String getS1Name() {
        return s1.getName();
    }

    public String getS2Name() {
        return s2.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tuple tuple = (Tuple) obj;
        return strFormat.equals(tuple.strFormat) || strFormat.equals(tuple.strFormatReverse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s1.getName(), s2.getName()) + Objects.hash(s2.getName(), s1.getName());
    }
}