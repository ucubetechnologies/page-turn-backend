package com.pageturn.controller;

import com.pageturn.dto.AnswerRequest;
import com.pageturn.dto.QuestionRequest;
import com.pageturn.entity.Answer;
import com.pageturn.entity.Question;
import com.pageturn.service.QaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QaController {

    private final QaService qaService;

    @GetMapping("/books/{bookId}/qa")
    public ResponseEntity<List<Question>> getBookQa(@PathVariable Long bookId) {
        return ResponseEntity.ok(qaService.getQuestionsForBook(bookId));
    }

    @GetMapping("/qa")
    public ResponseEntity<List<Question>> getAllQa() {
        return ResponseEntity.ok(qaService.getAllQuestions());
    }

    @PostMapping("/books/{bookId}/questions")
    public ResponseEntity<Question> createQuestion(@PathVariable Long bookId,
                                                   @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qaService.createQuestion(bookId, request));
    }

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<Answer> addAnswer(@PathVariable Long questionId,
                                            @Valid @RequestBody AnswerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qaService.addAnswer(questionId, request));
    }

    @PutMapping("/qa/{answerId}/status")
    public ResponseEntity<Answer> updateAnswerStatus(@PathVariable Long answerId,
                                                     @RequestBody Map<String, String> body) {
        Answer.Status status = Answer.Status.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(qaService.updateAnswerStatus(answerId, status));
    }
}
