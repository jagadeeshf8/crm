import React, { useState, useEffect } from 'react';
import './CustomerBehavior.css';

function CustomerBehavior() {
  const [reportData, setReportData] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCustomerBehaviorReport();
  }, []);

  const fetchCustomerBehaviorReport = async () => {
    try {
      const token = localStorage.getItem('token'); // Retrieve JWT token from localStorage
      const response = await fetch(
        'http://localhost:8084/api/reports/customer-behavior',
        {
          headers: {
            Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
          },
        }
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.text();
      setReportData(data);
      setError('');
    } catch (error) {
      console.error('Error fetching customer behavior report:', error);
      setError('Failed to fetch customer behavior report: ' + error.message);
    }
  };

  // Simple function to parse the text report into a more structured format for display
  const parseReport = (reportText) => {
    const lines = reportText.trim().split('\n').filter((line) => line.includes(':'));
    const headers = ['Customer', 'Sales Count', 'Average Sales Value'];
    const data = lines.map((line) => {
      const parts = line.split(',').map((part) => part.split(':')[1]?.trim());
      return parts;
    });
    return { headers, data };
  };

  const { headers, data } = parseReport(reportData);

  return (
    <div className="customer-behavior-container">
      <h2>Customer Behavior</h2>
      {error && <p className="error">{error}</p>}
      {reportData && data.length > 0 ? (
        <table>
          <thead>
            <tr>
              {headers.map((header, index) => (
                <th key={index}>{header}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.map((row, index) => (
              <tr key={index}>
                {row.map((cell, cellIndex) => (
                  <td key={cellIndex}>{cell}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>{reportData ? 'No customer behavior data available.' : 'Loading customer behavior data...'}</p>
      )}
    </div>
  );
}

export default CustomerBehavior;