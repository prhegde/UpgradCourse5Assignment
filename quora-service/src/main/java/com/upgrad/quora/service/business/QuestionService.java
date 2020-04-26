package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDAO;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Service
public class QuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns QuestionEntity by question ID
     *
     * @param questionId
     * @return QuestionEntity
     */
    public QuestionEntity getQuestionByQuestionId(String questionId) throws InvalidQuestionException {
        QuestionEntity questionEntity = questionDAO.getQuestionByQuestionId(questionId);
        if (questionEntity == null) {
            throw new InvalidQuestionException("QUES-001", "The question entered is invalid");
        }
        return questionEntity;
    }

    /**
     * Return true if question is already exist
     *
     * @param questionId
     * @return boolean
     * @throws InvalidQuestionException
     */
    public boolean isQuestionExist(String questionId) {
        return questionDAO.getQuestionByQuestionId(questionId) != null;
    }

    /**
     * Submits user question
     *
     * @param questionEntity
     * @return QuestionEntity
     */
    public QuestionEntity submitQuestion(QuestionEntity questionEntity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(questionEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return questionEntity;
    }
}
