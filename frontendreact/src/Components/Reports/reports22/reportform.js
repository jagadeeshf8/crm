import React, { useState, useEffect } from "react";
import axios from "axios";
import "./reportform.css";

const API_URL = "http://localhost:8084/api/reports";

function ReportForm({ onFormSubmit, existingReport }) {
  const [formData, setFormData] = useState({
    reportType: "",
    generatedDate: "",
    dataPoints: "",
  });

  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    if (existingReport) {
      setFormData(existingReport);
    } else {
      setFormData({
        reportType: "",
        generatedDate: "",
        dataPoints: "",
      });
    }
    setErrors({});
    setSuccessMessage("");
  }, [existingReport]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token"); // Retrieve JWT token from localStorage
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };

      if (existingReport && existingReport.reportId) {
        await axios.put(`${API_URL}/${existingReport.reportId}`, formData, config);
        setSuccessMessage("Report updated successfully!");
      } else {
        await axios.post(API_URL, formData, config);
        setSuccessMessage("Report added successfully!");
      }

      setFormData({
        reportType: "",
        generatedDate: "",
        dataPoints: "",
      });
      setErrors({});

      setTimeout(() => {
        setSuccessMessage("");
        onFormSubmit();
      }, 2000);
    } catch (error) {
      if (error.response && error.response.data) {
        // Map backend error response to the errors state
        setErrors(error.response.data);
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
        <label>Report Type:</label>
        <input
          name="reportType"
          value={formData.reportType}
          onChange={handleChange}
        />
        {errors.reportType && <p className="error">{errors.reportType}</p>} {/* Display error */}
      </div>

      <div className="form-group">
        <label>Generated Date:</label>
        <input
          type="date"
          name="generatedDate"
          value={formData.generatedDate}
          onChange={handleChange}
        />
        {errors.generatedDate && (
          <p className="error">{errors.generatedDate}</p>
        )} {/* Display error */}
      </div>

      <div className="form-group">
        <label>Data Points:</label>
        <textarea
          name="dataPoints"
          value={formData.dataPoints}
          onChange={handleChange}
        />
        {errors.dataPoints && <p className="error">{errors.dataPoints}</p>} {/* Display error */}
      </div>

      <div className="form-buttons">
        <button type="submit" className="add-btn">
          {existingReport ? "Update" : "Add"} Report
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

export default ReportForm;