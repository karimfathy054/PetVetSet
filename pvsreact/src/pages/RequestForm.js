import { Link } from "react-router-dom"
import styles from "../CSS/styleRequest.module.css"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { useEffect } from "react";
import axios from "axios";

// import React, { useState } from 'react';
// import styles from './ProductUploadForm.module.css';



const ProductUploadForm = () => {
  const [product, setProduct] = useState({
    productName: '',
    description: '',
    price: '',
    image: null,
    brandName:'',
  });
  const [selectedValue, setSelectedValue] = useState('');

const handleSelectChange = (e) => {
  console.log(selectedValue2);
    setSelectedValue(e.target.value);
  };
  const [selectedValue2, setSelectedValue2] = useState('');

  const handleSelectChange2 = (e) => {
    console.log(selectedValue2);
      setSelectedValue2(e.target.value);
    };
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    console.log(file);
    setProduct((prevProduct) => ({
      ...prevProduct,
      image: file,
    }));
  };
  const token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmRvMkBnbWFpbC5jb20iLCJpYXQiOjE3MDIzNzM4ODQsImV4cCI6MTcwMjQ2MDI4NH0.rZzFcP6Yk3IBIdG4HaZrKZmHhSNIc9rjoRBLUiuqmj8' 
  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:8080/api/addNewProduct', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({
          name : product.productName,
          brandName : product.brandName,
          description : product.description,
          price : product.price ,
          targetAnimal : selectedValue,
          categoryName: selectedValue2,
          userEmail :'abdo@gmail.com' ,
          
        }),
    })
        .then(response => response.json())
        .then(data => {
          console.log(data);
        })
        setProduct({
          productName: '',
          description: '',
          price: '',
          image: null,
          brandName:'',
        });
}
  return (
    <form className={styles.msform}>
    <fieldset>
    <div className={styles.productFormContainer}>
      <h2 className={styles.label}>Product Upload Form</h2>
      <form onSubmit={handleSubmit}>
        <label className={styles.label}>
          Product Name
          <input className={styles.input2}
            type="text"
            name="productName"
            value={product.productName}
            onChange={handleInputChange}
            required
          />
        </label>

        <label className={styles.label}>
          Description
          <textarea className={styles.input2}
            name="description"
            value={product.description}
            onChange={handleInputChange}
            required
          />
        </label  >

        <label className={styles.labe3}>
          Price
          <input className={styles.input3}
            name="price"
            value={product.price}
            onChange={handleInputChange}
            required
            
          />
        </label>
        <label className={styles.labe3}>
          Brand name
          <input className={styles.input3}
            name="brandName"
            value={product.brandName}
            onChange={handleInputChange}
            required
            
          />
        </label>

        <label className={styles.labe3}>
          Image
          <input className={styles.input1}
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            required
          />
        </label >

      <label className={styles.select1} htmlFor="mySelect">Select a Category:</label>
      <select className={styles.select_button}id="mySelect" value={selectedValue} onChange={handleSelectChange}>
        <option value="">Select</option>
        <option value="food">food</option>
        <option value="accessories">accessories</option>
        <option value="artiika">artiika</option>
        <option value="frontline">frontline</option>
        <option value="canin">canin</option>
        <option value="toys">toys</option>
        <option value="medicine">medicine</option>
      </select>
      <label className={styles.select2} htmlFor="mySelect2">Select a Target animal:</label>
      <select className={styles.select_button}id="mySelect2" value={selectedValue2} onChange={handleSelectChange2}>
        <option value="">Select</option>
        <option value="dog">dog</option>
        <option value="cat">cat</option>
        <option value="bird">bird</option>
        <option value="pet">pet</option>
      </select>
      

        <button className={styles.button} type="submit">Upload Product</button>
      </form>
    </div>
    </fieldset>
    </form>
  );
};

export default ProductUploadForm;
