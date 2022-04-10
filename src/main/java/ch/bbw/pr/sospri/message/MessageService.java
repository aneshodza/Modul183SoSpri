package ch.bbw.pr.sospri.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * MessageService
 * 
 * @author Peter Rutschmann
 * @version 25.06.2020
 */
@Service
public class MessageService {
	@Autowired
	private MessageRepository repository;
	
	public Iterable<Message> getAll(){
		return repository.findAll();
	}

	public void add(Message message) {
		repository.save(message);
	}

	public void update(Long id, Message message) {
		Message message1 = repository.findById(id).get();
		message1.setContent(message.getContent());
		repository.save(message1);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Message getById(Long id) {
		return repository.findById(id).get();
	}
}
