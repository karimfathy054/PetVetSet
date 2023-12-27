import styles from "../CSS/styleRequest.module.css"
import { User } from '../User'
import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react";
import ProductListHeader from "../ProductListComponents/ProductListHeader";
const ProductUploadForm = () => {
  // start
  const location = useLocation();
  const [user, setUser] = useState({});
  const [temp, setTemp] = useState(true);
  useEffect(() => {
    if (location.state != null) {
      setUser(location.state.user);
      setTemp(false);
    }
  }
  )
  //end


  const [selectedValue, setSelectedValue] = useState('food');
  const [selectedValue2, setSelectedValue2] = useState('pet');
  const [productName, setproductName] = useState('');
  const [Price, setPrice] = useState('');
  const [brandName, setBrandName] = useState('');
  const [Description, setDescription] = useState('');
  const [image, setImage] = useState('');
  const [responseText, setResponseText] = useState(null);
  const handleSelectChange = (e) => {
    setSelectedValue(e.target.value);
    console.log(selectedValue);
  }

  const handleTargetAnimal = (e) => {
    setSelectedValue2(e.target.value);
    console.log(selectedValue2);
  }
  const handleProductName = (e) => {
    setproductName(e.target.value);
    console.log(productName);
  }
  const handleDescription = (e) => {
    setDescription(e.target.value);
    console.log(Description);
  }
  const handlePrice = (e) => {
    setPrice(e.target.value);

  }
  const handleBrandName = (e) => {
    setBrandName(e.target.value);
    console.log(brandName);
  }


  const handleFileChange = (e) => {
    const file = e.target.files[0];
    console.log(file.name);
    setImage(file.name);
  }
  const handleSubmit = async (e) => {
    if (Price.trim() === "" || Number.isNaN(Number(Price))) {
      window.alert("Enter number in the price.");
      setPrice("");
      console.log(Price);
    } else {
      e.preventDefault();
      const response = await fetch('http://localhost:8080/api/addNewProduct', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${user.token}`,
        },
        body: JSON.stringify({
          name: productName,
          brandName: brandName,
          description: Description,
          price: Price,
          targetAnimal: selectedValue2,
          categoryName: selectedValue,
          userEmail: user.email,
          photo: image,

        }),
      })

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      // Assuming the server returns a string
      const textResponse = await response.text();
      console.log(textResponse);
      if (textResponse == "Added to database...") {
        window.alert("Uploading success and waiting For admin acceptance");
      }

      setBrandName("");
      setDescription("");
      setImage(null);
      setPrice("");
      setproductName("");
      setSelectedValue("");
      setSelectedValue2("");
    }


  }
  return (
    <>
      <ProductListHeader user={user} />
      <div className={styles.mainClass}>
        <form className={styles.msform}>
          <fieldset className={styles.request}>
            <div className={styles.productFormContainer}>
              <h2 className={styles.label}>Product Upload Form</h2>
              <form >
                <label className={styles.label}>
                  Product Name
                  <input className={styles.input2}
                    type="text"
                    name="productName"
                    value={productName}
                    onChange={handleProductName}
                    required
                  />
                </label>

                <label className={styles.label}>
                  Description
                  <textarea className={styles.input2}
                    name="description"
                    value={Description}
                    onChange={handleDescription}
                    required
                  />
                </label  >

                <label className={styles.labe3}>
                  Price
                  <input className={styles.input3}
                    name="price"
                    value={Price}
                    onChange={handlePrice}
                    required

                  />
                </label>
                <label className={styles.labe3}>
                  Brand name
                  <input className={styles.input3}
                    name="brandName"
                    value={brandName}
                    onChange={handleBrandName}
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
                <div className={styles.select3}>
                  <label className={styles.select1} htmlFor="mySelect">Select a Category:</label>
                  <select className={styles.select_button} id="mySelect" value={selectedValue} onChange={handleSelectChange}>
                    <option value="food">food</option>
                    <option value="accessories">accessories</option>
                    <option value="artiika">artiika</option>
                    <option value="frontline">frontline</option>
                    <option value="canin">canin</option>
                    <option value="toys">toys</option>
                    <option value="medicine">medicine</option>
                    required
                  </select>
                </div>
                <div className={styles.select3}>
                  <label className={styles.select2} htmlFor="mySelect">Select a Target animal:</label>
                  <select className={styles.select_button} id="mySelect2" value={selectedValue2} onChange={handleTargetAnimal}>
                    <option value="pet">pet</option>
                    <option value="dog">dog</option>
                    <option value="cat">cat</option>
                    <option value="bird">bird</option>
                    required
                  </select>
                </div>
                <button className={styles.button} type="submit" onClick={handleSubmit}>Upload Product</button>
              </form>
            </div>
          </fieldset>
        </form>
      </div>
    </>
  );
};

export default ProductUploadForm;
