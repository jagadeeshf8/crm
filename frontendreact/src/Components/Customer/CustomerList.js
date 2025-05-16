import React, { useEffect, useState } from "react";
import axios from "axios";
import CustomerForm from "./CustomerForm";
import "./CustomerList.css";

const API_URL = "http://localhost:8084/customers";

function CustomerList() {
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [filterType, setFilterType] = useState("");
  const [filterValue, setFilterValue] = useState("");
  const [error, setError] = useState(""); // Added error state

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      const token = localStorage.getItem("token"); // Retrieve JWT token from localStorage
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };
      const response = await axios.get(API_URL, config);
      setCustomers(response.data);
      setError(""); // Clear any previous errors
    } catch (error) {
      console.error("Error fetching customers:", error);
      setError("Failed to fetch customers. Please try again."); // Set error message
    }
  };

  const fetchFilteredCustomers = async () => {
    if (!filterType || !filterValue) {
      alert("Please select a filter type and value");
      return;
    }
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const response = await axios.get(
        `${API_URL}/categorize/${filterType}/${filterValue}`,
        config
      );
      setCustomers(response.data);
      setError("");
    } catch (error) {
      console.error("Error fetching filtered customers:", error);
      setError("Failed to fetch filtered customers. Please try again.");
    }
  };

  const deleteCustomer = async (id) => {
    if (window.confirm("Are you sure you want to delete this customer?")) {
      try {
        const token = localStorage.getItem("token");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        await axios.delete(`${API_URL}/${id}`, config);
        fetchCustomers(); // Refresh the customer list
      } catch (error) {
        console.error("Error deleting customer:", error);
        setError("Failed to delete customer. Please try again.");
      }
    }
  };

  const handleEdit = (customer) => {
    setSelectedCustomer(customer);
    setShowForm(true);
  };

  const handleFormSubmit = () => {
    setSelectedCustomer(null);
    setShowForm(false);
    fetchCustomers(); // Refresh the customer list after form submission
  };

  return (
    <div className="customer-list-container">
      {!showForm ? (
        <>
          <div className="filter-container">
            <select
              value={filterType}
              onChange={(e) => setFilterType(e.target.value)}
            >
              <option value="">Select Filter Type</option>
              <option value="region">Region</option>
              <option value="interest">Interest</option>
              <option value="habit">Purchasing Habit</option>
            </select>
            <input
              type="text"
              placeholder="Enter filter value"
              value={filterValue}
              onChange={(e) => setFilterValue(e.target.value)}
            />
            <button className="filter-button" onClick={fetchFilteredCustomers}>
              Filter
            </button>
          </div>
          <button
            className="add-customer-button"
            onClick={() => setShowForm(true)}
          >
            Add Customer
          </button>
          <h2>Customer List</h2>
          {error && <p className="error">{error}</p>} {/* Display error if present */}
          <table className="customer-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Contact Info</th>
                <th>Purchase History</th>
                <th>Segmentation Data</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {customers.map((cust) => (
                <tr key={cust.customerID}>
                  <td>{cust.customerID}</td>
                  <td>{cust.name}</td>
                  <td>{cust.contactInfo}</td>
                  <td>{cust.purchaseHistory}</td>
                  <td>{cust.segmentationData}</td>
                  <td>
                    <button
                      className="edit-button"
                      onClick={() => handleEdit(cust)}
                    >
                      Edit
                    </button>
                    <button
                      className="delete-button"
                      onClick={() => deleteCustomer(cust.customerID)}
                    >
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
            {selectedCustomer ? "Edit Customer" : "Add Customer"}
          </h2>
          <CustomerForm
            onFormSubmit={handleFormSubmit}
            existingCustomer={selectedCustomer}
          />
        </>
      )}
    </div>
  );
}

export default CustomerList;