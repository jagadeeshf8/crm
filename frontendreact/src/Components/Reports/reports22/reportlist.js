import React, { useState, useEffect } from "react";
import axios from "axios";
import ReportForm from "./reportform";
import "./reportlist.css";

const API_URL = "http://localhost:8084/api/reports";

function ReportList() {
  const [reports, setReports] = useState([]);
  const [filterType, setFilterType] = useState(""); // For filtering reports
  const [selectedReport, setSelectedReport] = useState(null); // Track the selected report for editing
  const [isFormVisible, setIsFormVisible] = useState(false); // Toggle form visibility

  useEffect(() => {
    fetchReports();
  }, []);

  const fetchReports = async () => {
    try {
      const token = localStorage.getItem("token"); 
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      };
      const res = await axios.get(API_URL, config);
      setReports(res.data);
    } catch (error) {
      alert("Error fetching reports");
    }
  };

  const fetchFilteredReports = async () => {
    if (!filterType) {
      alert("Please select a report type to filter");
      return;
    }
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const res = await axios.get(`${API_URL}/categorize/${filterType}`, config);
      setReports(res.data);
    } catch (error) {
      alert("Error fetching filtered reports");
    }
  };

  const refreshReports = async () => {
    try {
      const token = localStorage.getItem("token");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const res = await axios.get(`${API_URL}/refresh`, config);
      setReports(res.data);
    } catch (error) {
      alert("Error refreshing reports");
    }
  };

  const handleAddReport = () => {
    setSelectedReport(null); // Clear selected report for adding a new one
    setIsFormVisible(true);
  };

  const handleEdit = (report) => {
    setSelectedReport(report); // Set the selected report for editing
    setIsFormVisible(true);
  };

  const handleDelete = async (reportId) => {
    if (window.confirm("Are you sure you want to delete this report?")) {
      try {
        const token = localStorage.getItem("token");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        await axios.delete(`${API_URL}/${reportId}`, config);
        alert("Report deleted successfully");
        fetchReports(); // Refresh the report list after deletion
      } catch (error) {
        alert("Error deleting report");
      }
    }
  };

  const handleFormSubmit = () => {
    setIsFormVisible(false); // Hide the form after submission
    fetchReports(); // Refresh the report list
  };

  return (
    <div className="report-list-container">
      {!isFormVisible ? (
        <>
          <div className="header-container">
            <h2>Report List</h2>
            <button className="add-button" onClick={handleAddReport}>
              Add Report
            </button>
          </div>
          <div className="filter-container">
            <select
              value={filterType}
              onChange={(e) => setFilterType(e.target.value)}
            >
              <option value="">Select Report Type</option>
              <option value="Sales">Sales</option>
              <option value="CustomerBehavior">CustomerBehavior</option>
              <option value="MarketingOutcomes">MarketingOutcomes</option>
            </select>
            <button className="filter-button" onClick={fetchFilteredReports}>
              Filter
            </button>
            <button className="refresh-button" onClick={refreshReports}>
              Refresh
            </button>
          </div>
          <table className="report-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Generated Date</th>
                <th>Data Points</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {reports.map((report) => (
                <tr key={report.reportId}>
                  <td>{report.reportId}</td>
                  <td>{report.reportType}</td>
                  <td>{new Date(report.generatedDate).toLocaleDateString()}</td>
                  <td>{report.dataPoints}</td>
                  <td>
                    <button
                      className="edit-button"
                      onClick={() => handleEdit(report)}
                    >
                      Edit
                    </button>
                    <button
                      className="delete-button"
                      onClick={() => handleDelete(report.reportId)}
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
            {selectedReport ? "Edit Report" : "Add Report"}
          </h2>
          <ReportForm
            onFormSubmit={handleFormSubmit}
            existingReport={selectedReport}
          />
        </>
      )}
    </div>
  );
}

export default ReportList;