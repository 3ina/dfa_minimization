package dfa;

public class Tuple {
    private State s1;
    private State s2;

    String str_format;
    String str_format_reverse;

    public Tuple(State s1,State s2){
        this.s1 = s1;
        this.s2 = s2;
        this.str_format = "{"+this.s1.getName()+this.s2.getName()+"}";
        this.str_format_reverse = "{"+this.s2.getName()+this.s1.getName()+"}";

    }

    @Override
    public boolean equals(Object obj) {
        Tuple tuple = (Tuple) obj;
        if (tuple.str_format.compareTo(this.str_format) ==0  || tuple.str_format_reverse.compareTo(this.str_format)==0 ){
            return true;
        }

        return false;

    }

    public String getS1Name() {
        return s1.getName();
    }

    public String getS2Name() {
        return s2.getName();
    }
}
