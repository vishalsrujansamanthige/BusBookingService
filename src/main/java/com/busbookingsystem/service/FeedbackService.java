package com.busbookingsystem.service;

import java.util.List;



import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.exceptions.FeedBackException;
import com.busbookingsystem.exceptions.UserException;
import com.busbookingsystem.model.Feedback;

public interface FeedbackService {
	
public Feedback addFeedBack(Feedback feedBack,Integer busId,String key) throws BusException, UserException;
	
	public Feedback updateFeedBack(Feedback feedback,String key) throws FeedBackException, UserException;
	
	public Feedback deleteFeedBack(Integer feedbackId, String key)throws FeedBackException, UserException;

	public Feedback viewFeedback(Integer id) throws FeedBackException;

	public List<Feedback> viewFeedbackAll() throws FeedBackException;

}
