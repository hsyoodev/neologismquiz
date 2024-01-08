package com.example.neologismquiz;

public class QuizDto {
    private final String question;
    private final String answer;
    private final String wrong;

    public QuizDto(String question, String answer, String wrong) {
        this.question = question;
        this.answer = answer;
        this.wrong = wrong;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWrong() {
        return wrong;
    }

    @Override
    public String toString() {
        return "QuizDto{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", wrong='" + wrong + '\'' +
                '}';
    }
}
