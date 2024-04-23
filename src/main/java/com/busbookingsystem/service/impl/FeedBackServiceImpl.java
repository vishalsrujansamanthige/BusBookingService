package com.busbookingsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbookingsystem.exceptions.BusException;
import com.busbookingsystem.exceptions.FeedBackException;
import com.busbookingsystem.exceptions.UserException;
import com.busbookingsystem.model.Bus;
import com.busbookingsystem.model.CurrentUserSession;
import com.busbookingsystem.model.Feedback;
import com.busbookingsystem.model.User;
import com.busbookingsystem.repository.*;
import com.busbookingsystem.service.FeedbackService;

@Service
public class FeedBackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackDao fdao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BusDao busDao;

	@Autowired
	private UserSessionDao userSessionDao;

	@Override
	public Feedback addFeedBack(Feedback feedBack, Integer busId, String key) throws BusException, UserException {

		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to give Feedback!");

		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User Not Found"));

		Optional<Bus> busOptional = busDao.findById(busId);

		if (!busOptional.isPresent()) {
			throw new BusException("Bus is not present with Id" + busId);
		}

		feedBack.setBus(busOptional.get());
		feedBack.setUser(user);
		feedBack.setFeedbackDateTime(LocalDateTime.now());
		Feedback savedFeedback = fdao.save(feedBack);

		return savedFeedback;

	}

	@Override
	public Feedback updateFeedBack(Feedback feedback, String key) throws FeedBackException, UserException {
		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update FeedBack");

		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));
		Optional<Feedback> optFeedback = fdao.findById(feedback.getFeedbackId());

		if (optFeedback.isPresent()) {

			Feedback feedback2 = optFeedback.get();

			Optional<Bus> optBus = busDao.findById(feedback2.getBus().getBusId());

			if (!optBus.isPresent()) {
				throw new FeedBackException("Invalid bus details!");
			}

			feedback.setBus(optBus.get());
			feedback.setUser(user);
			feedback.setFeedbackDateTime(LocalDateTime.now());
			return fdao.save(feedback);
		}
		throw new FeedBackException("No feedback found!");
	}

	@Override
	public Feedback deleteFeedBack(Integer feedbackId, String key) throws FeedBackException, UserException {
		CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Feedback!");
		}

		User user = userDao.findById(loggedInUser.getUserId()).orElseThrow(() -> new UserException("User not found!"));

		Optional<Feedback> fedOptional = fdao.findById(feedbackId);

		if (fedOptional.isPresent()) {

			Feedback existingFeedback = fedOptional.get();

			fdao.delete(existingFeedback);

			return existingFeedback;

		}
		throw new FeedBackException("No feedback found!");

	}

	@Override
	public Feedback viewFeedback(Integer id) throws FeedBackException {
		Optional<Feedback> fedOptional = fdao.findById(id);
		if (fedOptional.isPresent()) {

			return fedOptional.get();

		}
		throw new FeedBackException("No feedback found!");
	}

	@Override
	public List<Feedback> viewFeedbackAll() throws FeedBackException {
		Optional<List<Feedback>> fedOptional = Optional.of(fdao.findAll());
		if (fedOptional.isPresent()) {

			return fedOptional.get();

		}
		throw new FeedBackException("No feedbacks found!");

	}

}
