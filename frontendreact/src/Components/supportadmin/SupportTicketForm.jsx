import React, { useState, useEffect } from "react";
import axios from "axios";
import "./SupportTicketForm.css";

const API_URL = "http://localhost:8084/api/ticket";

function SupportTicketForm({ onFormSubmit, existingTicket }) {
  const [formData, setFormData] = useState({
    customer: { customerId: "" }, // Updated to use customerId
    issueDescription: "",
    assignedAgent: "",
    status: "Open", // Default status
  });

  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    if (existingTicket) {
      setFormData({
        customer: { customerId: existingTicket.customer ? existingTicket.customer.customerId : "" },
        issueDescription: existingTicket.issueDescription || "",
        assignedAgent: existingTicket.assignedAgent || "",
        status: existingTicket.status || "Open",
      });
    } else {
      setFormData({
        customer: { customerId: "" },
        issueDescription: "",
        assignedAgent: "",
        status: "Open",
      });
    }
    setErrors({});
    setSuccessMessage("");
  }, [existingTicket]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "customerId") {
      setFormData({ ...formData, customer: { customerId: value } });
    } else {
      setFormData({ ...formData, [name]: value });
      setErrors({ ...errors, [name]: "" });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token"); // Retrieve JWT token
      if (!token) {
        throw new Error("No token found. Please log in again.");
      }

      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };

      // Prepare the payload to match the backend's expected structure
      const payload = {
        customerId: formData.customer.customerId, // Ensure this matches the backend field name
        issueDescription: formData.issueDescription,
        assignedAgent: formData.assignedAgent,
        status: formData.status,
      };

      if (existingTicket && existingTicket.ticketID) {
        await axios.put(`${API_URL}/${existingTicket.ticketID}`, payload, config);
        setSuccessMessage("Ticket updated successfully!");
      } else {
        await axios.post(API_URL, payload, config);
        setSuccessMessage("Ticket added successfully!");
      }

      setFormData({
        customer: { customerId: "" },
        issueDescription: "",
        assignedAgent: "",
        status: "Open",
      });
      setErrors({});

      setTimeout(() => {
        setSuccessMessage("");
        onFormSubmit();
      }, 2000);
    } catch (error) {
      if (error.response && error.response.data && error.response.data.details) {
        setErrors(error.response.data.details);
      } else {
        alert("Error: " + error.message);
      }
    }
  };

  return (
    <form className="form-container" onSubmit={handleSubmit}>
      {successMessage && (
        <div className="toast-success">
          {successMessage} <span className="check-icon">✔</span>
        </div>
      )}

      <div className="form-group">
        <label>Customer ID:</label>
        <input
          name="customerId"
          value={formData.customer.customerId}
          onChange={handleChange}
          required
        />
        {errors.customer && errors.customer.customerId && <p className="error">{errors.customer.customerId}</p>}
      </div>

      <div className="form-group">
        <label>Issue Description:</label>
        <textarea
          name="issueDescription"
          value={formData.issueDescription}
          onChange={handleChange}
          required
        />
        {errors.issueDescription && <p className="error">{errors.issueDescription}</p>}
      </div>

      <div className="form-group">
        <label>Assigned Agent:</label>
        <input
          name="assignedAgent"
          value={formData.assignedAgent}
          onChange={handleChange}
        />
        {errors.assignedAgent && <p className="error">{errors.assignedAgent}</p>}
      </div>

      <div className="form-group">
        <label>Status:</label>
        <select
          name="status"
          value={formData.status}
          onChange={handleChange}
          required
        >
          <option value="Open">Open</option>
          <option value="Closed">Closed</option>
        </select>
        {errors.status && <p className="error">{errors.status}</p>}
      </div>

      <div className="form-buttons">
        <button type="submit" className="add-btn">
          {existingTicket ? "Update" : "Add"} Ticket
        </button>
        <button
          type="button"
          className="back-btn"
          onClick={onFormSubmit}
        >
          Back to List
        </button>
      </div>
    </form>
  );
}

export default SupportTicketForm;