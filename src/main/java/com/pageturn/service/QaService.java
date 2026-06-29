package com.pageturn.service;

import com.pageturn.dto.AnswerRequest;
import com.pageturn.dto.QuestionRequest;
import com.pageturn.entity.Answer;
import com.pageturn.entity.Book;
import com.pageturn.entity.Question;
import com.pageturn.entity.User;
import com.pageturn.exception.ResourceNotFoundException;
import com.pageturn.repository.AnswerRepository;
import com.pageturn.repository.BookRepository;
import com.pageturn.repository.QuestionRepository;
import com.pageturn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QaService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Question> getQuestionsForBook(Long bookId) {
        return questionRepository.findByBookId(bookId);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question createQuestion(Long bookId, QuestionRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        Question question = Question.builder()
                .book(book)
                .user(user)
                .questionText(request.getQuestionText())
                .date(LocalDate.now())
                .build();
        return questionRepository.save(question);
    }

    public Answer addAnswer(Long questionId, AnswerRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + questionId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        Answer answer = Answer.builder()
                .question(question)
                .user(user)
                .body(request.getBody())
                .date(LocalDate.now())
                .status(Answer.Status.PENDING)
                .build();
        return answerRepository.save(answer);
    }

    public Answer updateAnswerStatus(Long answerId, Answer.Status status) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found: " + answerId));
        answer.setStatus(status);
        return answerRepository.save(answer);
    }
}
