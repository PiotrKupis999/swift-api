import React, { useState } from 'react';
import axios from 'axios';

const GetBanksByCountry = () => {
    const [countryISO2, setCountryISO2] = useState('');
    const [countryDetails, setCountryDetails] = useState(null);
    const [message, setMessage] = useState('');

    const handleSearch = async (e) => {
        e.preventDefault();
        setMessage('');
        setCountryDetails(null);

        try {
            const response = await axios.get(`http://localhost:8080/v1/swift-codes/country/${countryISO2}`);
            setCountryDetails(response.data);
        } catch (error) {
            setMessage('Error: ' + (error.response?.data?.message || error.message));
        }
    };

    return (
        <div>
            <h2>Find Banks by Country</h2>
            <form onSubmit={handleSearch}>
                <input
                    type="text"
                    placeholder="Country ISO2"
                    onChange={(e) => setCountryISO2(e.target.value)}
                    value={countryISO2}
                />
                <button type="submit">Search</button>
            </form>

            {message && <p style={{ color: 'red' }}>{message}</p>}

            {countryDetails && (
                <div>
                    <h3>Country details:</h3>
                    <p><strong>Country Name:</strong> {countryDetails.countryName}</p>
                    <p><strong>Country ISO2:</strong> {countryDetails.countryISO2}</p>

                    {countryDetails.swiftCodes && countryDetails.swiftCodes.length > 0 && (
                        <div>
                            <h3>Banks:</h3>
                            <ul>
                                {countryDetails.swiftCodes.map((bank, index) => (
                                    <li key={index}>
                                        <p><strong>SWIFT Code:</strong> {bank.swiftCode}</p>
                                        <p><strong>Bank Name:</strong> {bank.bankName}</p>
                                        <p><strong>Address:</strong> {bank.address}</p>
                                        <p><strong>Headquarter:</strong> {bank.isHeadquarter ? 'Yes' : 'No'}</p>
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

export default GetBanksByCountry;
