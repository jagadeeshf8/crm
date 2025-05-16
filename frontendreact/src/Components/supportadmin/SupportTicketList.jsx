import React, { useEffect, useState } from "react";
import axios from "axios";
import SupportTicketForm from "./SupportTicketForm";
import "./SupportTicketList.css";

const API_URL = "http://localhost:8084/api/ticket";

function SupportTicketList() {
  const [tickets, setTickets] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const token = localStorage.getItem("token"); // Retrieve JWT token
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };
      const res = await axios.get(API_URL, config);
      setTickets(res.data);
    } catch (error) {
      alert("Error fetching support tickets");
    }
  };

  const deleteTicket = async (id) => {
    if (window.confirm("Are you sure you want to delete this ticket?")) {
      try {
        const token = localStorage.getItem("token"); // Retrieve JWT token
        const config = {
          headers: {
            Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
          },
        };
        await axios.delete(`${API_URL}/${id}`, config);
        fetchTickets();
      } catch (error) {
        alert("Error deleting ticket");
      }
    }
  };

  const handleEdit = (ticket) => {
    setSelectedTicket(ticket);
    setShowForm(true);
  };

  const handleFormSubmit = () => {
    setSelectedTicket(null);
    setShowForm(false);
    fetchTickets();
  };

  return (
    <div className="support-ticket-list-container">
      {!showForm ? (
        <>
          <button className="add-ticket-button" onClick={() => setShowForm(true)}>
            Add Ticket
          </button>
          <h2>Support Ticket List</h2>
          <table className="support-ticket-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Customer ID</th>
                <th>Issue Description</th>
                <th>Assigned Agent</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {tickets.map((ticket) => (
                <tr key={ticket.ticketID}>
                  <td>{ticket.ticketID}</td>
                  <td>{ticket.customer && ticket.customer.customerID}</td>
                  <td>{ticket.issueDescription}</td>
                  <td>{ticket.assignedAgent}</td>
                  <td>{ticket.status}</td>
                  <td>
                    <button className="edit-button" onClick={() => handleEdit(ticket)}>
                      Edit
                    </button>
                    <button className="delete-button" onClick={() => deleteTicket(ticket.ticketID)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      ) : (
        <>
          <h2 className="form-header">
            {selectedTicket ? "Edit Ticket" : "Add Ticket"}
          </h2>
          <SupportTicketForm onFormSubmit={handleFormSubmit} existingTicket={selectedTicket} />
        </>
      )}
    </div>
  );
}

export default SupportTicketList;