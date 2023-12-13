import { Link } from "react-router-dom"
import styles from "../CSS/styleRequest.module.css"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { useEffect } from "react";
const ProductUploadForm = () => {
  const [product, setProduct] = useState({
    productName: '',
    description: '',
    price: '',
    image: null,
  });
  const [selectedValue, setSelectedValue] = useState('');

const handleSelectChange = (e) => {
    setSelectedValue(e.target.value);
    console.log(selectedValue);
  };
  const [selectedValue2, setSelectedValue2] = useState('');

  const handleSelectChange2 = (e) => {
      setSelectedValue2(e.target.value);
      console.log(selectedValue2);
    };
  const handleInputTargetAnimal = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setProduct((prevProduct) => ({
      ...prevProduct,
      image: file,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Perform product upload logic (e.g., send data to server)

    // Reset form
    setProduct({
      productName: '',
      description: '',
      price: '',
      image: null,
    });
  };

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

        <label className={styles.label}>
          Price
          <input className={styles.input2}
            name="price"
            value={product.price}
            onChange={handleInputChange}
            required
            
          />
        </label>

        <label className={styles.label}>
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
        <option value="option1">Option 1</option>
        <option value="option2">Option 2</option>
        <option value="option3">Option 3</option>
      </select>
      <label className={styles.select2} htmlFor="mySelect2">Select a Target animal:</label>
      <select className={styles.select_button}id="mySelect2" value={selectedValue2} onChange={handleInputTargetAnimal}>
        <option value="">Select</option>
        <option value="option4">Option 1</option>
        <option value="option5">Option 2</option>
        <option value="option6">Option 3</option>
      </select>
      

        <button className={styles.button} type="submit">Upload Product</button>
      </form>
    </div>
    </fieldset>
    </form>
  );
};

export default ProductUploadForm;
