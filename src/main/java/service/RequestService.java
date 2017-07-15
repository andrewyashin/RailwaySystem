package service;

import dto.Ticket;
import dto.TrainRoute;
import exception.InvalidDataBaseOperation;
import model.entity.Request;
import model.entity.User;

import java.util.List;

public interface RequestService {
    void reserveTickets(final List<Ticket> tickets) throws InvalidDataBaseOperation;
    Ticket makeTicket(String parameter, User user, TrainRoute trainRoute);

    List<Ticket> findAllTickets();
    List<Ticket> addTickets(Ticket ticket, Integer count);

    void cancelRequest(Ticket ticket);
    Request addRequest(Request request) throws InvalidDataBaseOperation;
}
