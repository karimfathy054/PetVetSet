import styles from "../CSS/HomeStyle.module.css"
import s1 from "../images/Service-1.jpg"
import s2 from "../images/Service-2.jpg"
import s3 from "../images/Service-3.jpg"
import s4 from "../images/Service-4.jpg"
import s5 from "../images/Service-5.jpg"
import s6 from "../images/Service-6.jpg"
import done from "../images/done.png"
import 'react-phone-number-input/style.css'
import PhoneInput from 'react-phone-number-input'
import { useState } from "react"
export default function Services({ token, decode }) {
    const [phone, setPhone] = useState();
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const [message, setMessage] = useState();

    const handleEnquire = () => {
        const contact = document.getElementById("serviceContact");
        contact.style.display = "block";
    }
    const handleCloseContact = () => {
        const contact = document.getElementById("serviceContact");
        contact.style.display = "none";
    }
    const handleServiceSubmit = (e) => {
        e.preventDefault();
        const done = document.getElementById("serviceDone");
        done.style.display = "block";
        const contact = document.getElementById("serviceContact");
        contact.style.display = "none";
    }
    const handleCloseDone = () => {
        const done = document.getElementById("serviceDone");
        done.style.display = "none";
        const contact = document.getElementById("serviceContact");
        contact.style.display = "none";
    }
    return (
        <div className={styles.services} id="services">
            <div className={styles.contact} id="serviceContact">
                <div className={styles.cover}>
                    <div className={styles.close} onClick={handleCloseContact}>x</div>
                    <h2>Send Enquiry</h2>
                    <form onSubmit={handleServiceSubmit}>
                        <PhoneInput id="phone" placeholder="Enter phone number" value={phone} onChange={setPhone} required />
                        <link rel="stylesheet" href="/css/react-phone-number-input/style.css" />
                        <input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required></input>
                        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required></input>
                        <textarea placeholder="Message" value={message} onChange={(e) => setMessage(e.target.value)}></textarea>
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
            <div className={styles.done} id="serviceDone">
                <div className={styles.cover}>
                    <div className={styles.close} onClick={handleCloseDone}>x</div>
                    <p>We Will Contact You</p>
                    <img src={done}></img>
                    <button onClick={handleCloseDone}>Continue</button>
                </div>
            </div>
            <div className={styles.container}>
                <h2 className={styles.heading}>Services</h2>
                <div className={styles.content}>
                    <div className={styles.box}>
                        <img src={s1}></img>
                        <h3>Basic Training</h3>
                        <p>We train your pet with the foundation tricks and commands to make them well behaved and mannered.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                    <div className={styles.box}>
                        <img src={s2}></img>
                        <h3>Dog Hiring</h3>
                        <p>We also avail dogs for hiring for short time and have variety of breeds to choose your favorite one.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                    <div className={styles.box}>
                        <img src={s3}></img>
                        <h3>Advanced Training</h3>
                        <p>We have advanced courses which includes obedience and skilled commands training for your pet.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                    <div className={styles.box}>
                        <img src={s4}></img>
                        <h3>Lodging Facility</h3>
                        <p>Be it one day, weekend or long holidays, we have lodging services for your pets and take good care of them.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                    <div className={styles.box}>
                        <img src={s5}></img>
                        <h3>Advanced Dental Care</h3>
                        <p>We make sure that your pet have a healthy teeth and polish to improve quality of life of pet.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                    <div className={styles.box}>
                        <img src={s6}></img>
                        <h3>Pampering</h3>
                        <p>We offer your pet a clean, healthy, hygienic and loving environment to stay, while he is away from you.</p>
                        <div className={styles.service} onClick={handleEnquire}>Enquire Now</div>
                    </div>
                </div>
            </div>
        </div>
    )
}