import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import './SalePerformance.css';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const SalesPerformance = () => {
  const [lastYearSales, setLastYearSales] = useState(0);
  const [lastMonthSales, setLastMonthSales] = useState(0);
  const [lastWeekSales, setLastWeekSales] = useState(0);
  const [lastYearAveragePerMonth, setLastYearAveragePerMonth] = useState(0);
  const [lastYearAveragePerWeek, setLastYearAveragePerWeek] = useState(0);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchSalesData();
  }, []);

  const fetchSalesData = async () => {
    try {
      const token = localStorage.getItem('token'); // Retrieve JWT token from localStorage
      const config = {
        headers: {
          Authorization: `Bearer ${token}`, // Include JWT token in the Authorization header
        },
      };

      const lastYearResponse = await fetch(
        'http://localhost:8084/api/reports/sales-performance/last-year',
        config
      );
      const lastMonthResponse = await fetch(
        'http://localhost:8084/api/reports/sales-performance/last-month',
        config
      );
      const lastWeekResponse = await fetch(
        'http://localhost:8084/api/reports/sales-performance/last-week',
        config
      );
      const lastYearAveragePerMonthResponse = await fetch(
        'http://localhost:8084/api/reports/sales-performance/last-year-average-per-month',
        config
      );
      const lastYearAveragePerWeekResponse = await fetch(
        'http://localhost:8084/api/reports/sales-performance/last-year-average-per-week',
        config
      );

      if (
        !lastYearResponse.ok ||
        !lastMonthResponse.ok ||
        !lastWeekResponse.ok ||
        !lastYearAveragePerMonthResponse.ok ||
        !lastYearAveragePerWeekResponse.ok
      ) {
        throw new Error('Failed to fetch sales performance data');
      }

      const parseNumericValue = (responseText) => {
        const match = responseText.match(/[-+]?[0-9]*\.?[0-9]+/);
        return match ? parseFloat(match[0]) : 0;
      };

      setLastYearSales(parseNumericValue(await lastYearResponse.text()));
      setLastMonthSales(parseNumericValue(await lastMonthResponse.text()));
      setLastWeekSales(parseNumericValue(await lastWeekResponse.text()));
      setLastYearAveragePerMonth(
        parseNumericValue(await lastYearAveragePerMonthResponse.text())
      );
      setLastYearAveragePerWeek(
        parseNumericValue(await lastYearAveragePerWeekResponse.text())
      );
      setError('');
    } catch (error) {
      console.error('Error fetching sales data:', error);
      setError('Failed to load sales performance data. Please try again.');
    }
  };

  const salesPerMonthData = {
    labels: ['Last Year (Avg/Month)', 'Last Month'],
    datasets: [
      {
        label: '',
        data: [lastYearAveragePerMonth, lastMonthSales],
        backgroundColor: ['#3498db', '#2ecc71'],
      },
    ],
  };

  const salesPerWeekData = {
    labels: ['Last Year (Avg/Week)', 'Last Week'],
    datasets: [
      {
        label: '',
        data: [lastYearAveragePerWeek, lastWeekSales],
        backgroundColor: ['#9b59b6', '#e74c3c'],
      },
    ],
  };

  return (
    <div className="sales-performance-container">
      <h2>Sales Performance</h2>
      {error && <p className="error">{error}</p>}

      {/* Table at the top */}
      <table className="sales-table">
        <thead>
          <tr>
            <th>Period</th>
            <th>Sales</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Last Year</td>
            <td>{lastYearSales}</td>
          </tr>
          <tr>
            <td>Last Month</td>
            <td>{lastMonthSales}</td>
          </tr>
          <tr>
            <td>Last Week</td>
            <td>{lastWeekSales}</td>
          </tr>
        </tbody>
      </table>

      {/* Graphs below the table */}
      <div className="graphs-container">
        <div className="graph-container">
          <h3>Last Year Sales Per Month vs Last Month</h3>
          <Bar data={salesPerMonthData} />
          <div className="graph-labels">
            <span className="label last-year1">Last Year</span>
            <span className="label last-month">Last Month</span>
          </div>
        </div>
        <div className="graph-container">
          <h3>Last Year Sales Per Week vs Last Week</h3>
          <Bar data={salesPerWeekData} />
          <div className="graph-labels">
            <span className="label last-year">Last Year</span>
            <span className="label last-week">Last Week</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SalesPerformance;