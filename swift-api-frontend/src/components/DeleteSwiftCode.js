import React, { useState } from 'react';
import axios from 'axios';

const DeleteSwiftCode = () => {
    const [swiftCode, setSwiftCode] = useState('');
    const [bankName, setBankName] = useState('');
    const [countryISO2, setCountryISO2] = useState('');
    const [message, setMessage] = useState('');

    const handleDelete = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.delete(`http://localhost:8080/v1/swift-codes/${swiftCode}?bankName=${bankName}&countryISO2=${countryISO2}`);
            setMessage(response.data.message);
        } catch (error) {
            setMessage('Error: ' + error.message);
        }
    };

    return (
        <div>
            <h2>Delete SWIFT code</h2>
            <form onSubmit={handleDelete}>
                <input type="text" placeholder="SWIFT code" onChange={(e) => setSwiftCode(e.target.value)} value={swiftCode} /><br />
                <input type="text" placeholder="Bank Name" onChange={(e) => setBankName(e.target.value)} value={bankName} /><br />
                <input type="text" placeholder="Country ISO2 code" onChange={(e) => setCountryISO2(e.target.value)} value={countryISO2} /><br />
                <button type="submit">Delete</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default DeleteSwiftCode;
