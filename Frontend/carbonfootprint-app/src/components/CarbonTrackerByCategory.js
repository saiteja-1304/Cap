import React, { useState } from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const CarbonTrackerByCategory = () => {
  const [activeCategory, setActiveCategory] = useState(''); 
  const [data, setData] = useState([]);
  const [showChart, setShowChart] = useState(false);

  const fetchData = async (category) => {
    let endpoint = '';
    if (category === 'electricity') {
      endpoint = 'http://localhost:6688/carbonTrack/user/{userId}/electricity';
    } else if (category === 'transportation') {
      endpoint = 'http://localhost:6688/carbonTrack/user/{userId}/transportation';
    } else if (category === 'waste') {
      endpoint = 'http://localhost:6688/carbonTrack/user/{userId}/wastage';
    }

    try {
      const response = await fetch(endpoint);
      const result = await response.json();
      setData(result);
      setShowChart(true);
    } catch (error) {
      console.error(`Error fetching ${category} data:`, error);
    }
  };

  const handleButtonClick = (category) => {
    setActiveCategory(category);
    fetchData(category);
  };

  const renderEmissionsDetails = () => {
    switch (activeCategory) {
      case 'transportation':
        return (
          <div>
            <h3 className="text-xl font-semibold mb-2">Transportation Emissions</h3>
            {showChart && <LineChart data={data} label="Transportation Carbon Footprint" />}
          </div>
        );
      case 'electricity':
        return (
          <div>
            <h3 className="text-xl font-semibold mb-2">Electricity Emissions</h3>
            {showChart && <LineChart data={data} label="Electricity Carbon Footprint" />}
          </div>
        );
      case 'waste':
        return (
          <div>
            <h3 className="text-xl font-semibold mb-2">Waste Emissions</h3>
            {showChart && <LineChart data={data} label="Waste Carbon Footprint" />}
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="mt-8 max-w-4xl mx-auto">
      <h2 className="text-2xl font-semibold mb-4">Detailed Emissions</h2>
      <div className="flex justify-center space-x-4 mb-4">
        <button
          onClick={() => handleButtonClick('transportation')}
          className={`px-4 py-2 rounded ${activeCategory === 'transportation' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Transportation
        </button>
        <button
          onClick={() => handleButtonClick('electricity')}
          className={`px-4 py-2 rounded ${activeCategory === 'electricity' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Electricity
        </button>
        <button
          onClick={() => handleButtonClick('waste')}
          className={`px-4 py-2 rounded ${activeCategory === 'waste' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Waste
        </button>
      </div>
      <div className="bg-white p-4 rounded shadow-lg">
        {renderEmissionsDetails()}
      </div>
    </div>
  );
};

const LineChart = ({ data, label }) => {
  const chartData = {
    labels: data.map(item => item.date),
    datasets: [
      {
        label,
        data: data.map(item => item.value),
        borderColor: 'rgba(75, 192, 192, 1)',
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        fill: true,
      },
    ],
  };

  const options = {
    scales: {
      x: {
        type: 'category',
        title: {
          display: true,
          text: 'Date',
        },
      },
      y: {
        title: {
          display: true,
          text: 'Emissions (in kgCO2e)',
        },
      },
    },
  };

  return <Line data={chartData} options={options} />;
};

export default CarbonTrackerByCategory;
