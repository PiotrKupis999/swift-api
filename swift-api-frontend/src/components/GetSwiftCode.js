import React, { useState } from 'react';
import axios from 'axios';

const GetSwiftCode = () => {
    const [swiftCode, setSwiftCode] = useState('');
    const [bankDetails, setBankDetails] = useState(null);
    const [message, setMessage] = useState('');

    const handleSearch = async (e) => {
        e.preventDefault();
        setMessage('');
        setBankDetails(null);
        
        try {
            const response = await axios.get(`http://localhost:8080/v1/swift-codes/${swiftCode}`);
            setBankDetails(response.data);
        } catch (error) {
            setMessage('Error: ' + (error.response?.data?.message || error.message));
        }
    };

    return (
        <div>
            <h2>Find SWIFT code</h2>
            <form onSubmit={handleSearch}>
                <input 
                    type="text" 
                    placeholder="SWIFT Code" 
                    onChange={(e) => setSwiftCode(e.target.value)} 
                    value={swiftCode} 
                />
                <button type="submit">Search</button>
            </form>
            
            {message && <p>{message}</p>}

            {bankDetails && (
                <div>
                    <h3>Bank details:</h3>
                    <p><strong>SWIFT code:</strong> {bankDetails.swiftCode}</p>
                    <p><strong>Bank Name:</strong> {bankDetails.bankName}</p>
                    <p><strong>Address:</strong> {bankDetails.address}</p>
                    <p><strong>Country:</strong> {bankDetails.countryName} ({bankDetails.countryISO2})</p>
                    <p><strong>Headquarter:</strong> {bankDetails.isHeadquarter ? 'Yes' : 'No'}</p>

                    {bankDetails.isHeadquarter && bankDetails.branches.length > 0 && (
                        <div>
                            <h3>Branches:</h3>
                            <ul>
                                {bankDetails.branches.map((branch, index) => (
                                    <li key={index}>
                                        <p><strong>SWIFT:</strong> {branch.swiftCode}</p>
                                        <p><strong>Bank Name:</strong> {branch.bankName}</p>
                                        <p><strong>Address:</strong> {branch.address}</p>
                                        <p><strong>Country:</strong> {branch.countryName} ({branch.countryISO2})</p>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default GetSwiftCode;
