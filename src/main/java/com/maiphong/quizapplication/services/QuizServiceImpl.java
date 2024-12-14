package com.maiphong.quizapplication.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.quizapplication.dtos.quiz.QuizCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz.QuizMasterDTO;
import com.maiphong.quizapplication.dtos.quiz_question.QuizQuestionCreateEditDTO;
import com.maiphong.quizapplication.dtos.quiz_question.QuizQuestionMasterDTO;
import com.maiphong.quizapplication.entities.Question;
import com.maiphong.quizapplication.entities.Quiz;
import com.maiphong.quizapplication.entities.QuizQuestion;
import com.maiphong.quizapplication.exceptions.ResourceNotFoundException;
import com.maiphong.quizapplication.mappers.QuizMapper;
import com.maiphong.quizapplication.repositories.QuestionRepository;
import com.maiphong.quizapplication.repositories.QuizQuestionRepository;
import com.maiphong.quizapplication.repositories.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionRepository questionRepository,
            QuizQuestionRepository quizQuestionRepository,
            QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizMapper = quizMapper;
    }

    @Override
    public List<QuizMasterDTO> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        // convert dto to show view
        List<QuizMasterDTO> quizDTOs = quizzes.stream().map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        }).toList();

        return quizDTOs;
    }

    @Override
    public QuizMasterDTO getById(UUID id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);

        return quizDTO;

    }

    @Override
    public QuizMasterDTO create(QuizCreateEditDTO quizDTO) {
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz create should not empty");
        }

        Quiz newQuiz = quizMapper.toEntity(quizDTO);

        newQuiz = quizRepository.save(newQuiz);

        return quizMapper.toMasterDTO(newQuiz);

    }

    @Override
    public QuizMasterDTO update(UUID id, QuizCreateEditDTO quizDTO) {
        if (quizDTO == null) {
            throw new IllegalArgumentException("Quiz update should not empty");
        }

        Quiz updateQuiz = quizRepository.findById(id).orElse(null);

        if (updateQuiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        quizMapper.toEntity(quizDTO, updateQuiz);
        updateQuiz = quizRepository.save(updateQuiz);

        return quizMapper.toMasterDTO(updateQuiz);

    }

    @Override
    public boolean delete(UUID id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        quizRepository.delete(quiz);

        return !quizRepository.existsById(id);
    }

    @Override
    public Page<QuizMasterDTO> search(String keyword, Pageable pageable) {
        Specification<Quiz> spec = (root, _, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Quiz> quizPage = quizRepository.findAll(spec, pageable);

        Page<QuizMasterDTO> quizPageDTO = quizPage.map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        });

        return quizPageDTO;
    }

    @Override
    public List<QuizMasterDTO> searchByTitle(String keyword) {
        Specification<Quiz> spec = (root, _, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };

        List<Quiz> quizs = quizRepository.findAll(spec);

        List<QuizMasterDTO> masterDTOs = quizs.stream().map(quiz -> {
            QuizMasterDTO quizDTO = quizMapper.toMasterDTO(quiz);
            return quizDTO;
        }).toList();

        return masterDTOs;
    }

    @Override
    public boolean deleteQuestionFromQuiz(UUID quizId, UUID questionId) {
        if (quizId == null || questionId == null) {
            throw new IllegalArgumentException("Id is required");
        }

        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz is not found");
        }

        if (quiz.getQuizQuestions() != null && !quiz.getQuizQuestions().isEmpty()) {
            Question question = questionRepository.findById(questionId).orElse(null);

            if (question == null) {
                throw new ResourceNotFoundException("Question is not found");
            }

            questionRepository.delete(question);
        }

        // // Kiểm tra xem câu hỏi có nằm trong quiz không
        // Optional<QuizQuestion> quizQuestion =
        // quizQuestionRepository.findByQuizIdAndQuestionId(quizId, questionId);
        // if (!quizQuestion.isPresent()) {
        // throw new ResourceNotFoundException("This question is not found in the
        // specified quiz");
        // }

        // // Xóa mối quan hệ giữa quiz và question trong bảng trung gian
        // quizQuestionRepository.deleteByQuizIdAndQuestionId(quizId, questionId);

        return !questionRepository.existsById(questionId);

    }

    @Override
    public QuizQuestionMasterDTO addQuestionToQuiz(QuizQuestionCreateEditDTO masterDTO) {
        if (masterDTO == null) {
            throw new IllegalArgumentException("Quiz question id is required");
        }

        Quiz quiz = quizRepository.findById(masterDTO.getQuizId()).orElse(null);

        if (quiz == null) {
            throw new IllegalArgumentException("Quiz is not found");
        }

        Question question = questionRepository.findById(masterDTO.getQuestionId()).orElse(null);

        if (question == null) {
            throw new ResourceNotFoundException("Question is not found");
        }

        Optional<QuizQuestion> existQuestion = quizQuestionRepository.findByQuizIdAndQuestionId(masterDTO.getQuizId(),
                masterDTO.getQuestionId());

        if (existQuestion.isPresent()) {
            throw new IllegalArgumentException("Question is already exist in quiz");
        }

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuiz(quiz);
        quizQuestion.setQuestion(question);

        quizQuestionRepository.save(quizQuestion);

        QuizQuestionMasterDTO master = new QuizQuestionMasterDTO();
        master.setQuizId(masterDTO.getQuizId());
        master.setQuestionId(masterDTO.getQuestionId());

        return master;

    }

}
