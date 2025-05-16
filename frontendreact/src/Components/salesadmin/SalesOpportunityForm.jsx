import React, { useState, useEffect } from "react";
import axios from "axios";
import "./SalesOpportunityForm.css";

const API_URL = "http://localhost:8084/api/opportunity";

function SalesOpportunityForm({ onFormSubmit, existingOpportunity }) {
  const [formData, setFormData] = useState({
    customerID: "",
    salesStage: "",
    estimatedValue: "",
    closingDate: "",
  });

  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    if (existingOpportunity) {
      setFormData({
        customerID: existingOpportunity.customerID.customerId || "",
        salesStage: existingOpportunity.salesStage || "",
        estimatedValue: existingOpportunity.estimatedValue || "",
        closingDate: existingOpportunity.closingDate || "",
      });
    } else {
      setFormData({
        customerID: "",
        salesStage: "",
        estimatedValue: "",
        closingDate: "",
      });
    }
    setErrors({});
    setSuccessMessage("");
  }, [existingOpportunity]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setErrors({ ...errors, [name]: "" });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };

      if (existingOpportunity && existingOpportunity.opportunityID) {
        await axios.put(`${API_URL}/${existingOpportunity.opportunityID}`, formData, config);
        setSuccessMessage("Opportunity updated successfully!");
      } else {
        await axios.post(API_URL, formData, config);
        setSuccessMessage("Opportunity added successfully!");
      }

      setFormData({
        customerID: "",
        salesStage: "",
        estimatedValue: "",
        closingDate: "",
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
      {successMessage && <div className="toast-success">{successMessage}</div>}

      <div className="form-group">
        <label>Customer ID:</label>
        <input
          name="customerID"
          value={formData.customerID}
          onChange={handleChange}
          required
        />
        {errors.customerID && <p className="error">{errors.customerID}</p>}
      </div>

      <div className="form-group">
        <label>Sales Stage:</label>
        <input
          name="salesStage"
          value={formData.salesStage}
          onChange={handleChange}
          required
        />
        {errors.salesStage && <p className="error">{errors.salesStage}</p>}
      </div>

      <div className="form-group">
        <label>Estimated Value:</label>
        <input
          name="estimatedValue"
          type="number"
          value={formData.estimatedValue}
          onChange={handleChange}
          required
        />
        {errors.estimatedValue && <p className="error">{errors.estimatedValue}</p>}
      </div>

      <div className="form-group">
        <label>Closing Date:</label>
        <input
          name="closingDate"
          type="date"
          value={formData.closingDate}
          onChange={handleChange}
          required
        />
        {errors.closingDate && <p className="error">{errors.closingDate}</p>}
      </div>

      <div className="form-buttons">
        <button type="submit" className="add-btn">
          {existingOpportunity ? "Update" : "Add"} Opportunity
        </button>
        <button type="button" className="back-btn" onClick={onFormSubmit}>
          Back to List
        </button>
      </div>
    </form>
  );
}

export default SalesOpportunityForm;