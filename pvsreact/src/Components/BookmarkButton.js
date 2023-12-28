import React, { useState } from 'react';
import styles from '../CSS/BookmarkButton.module.css';
import { json } from 'react-router-dom';

const BookmarkButton = ({ onBookmarkToggle, productId, userId, user }) => {
  const [isBookmarked, setIsBookmarked] = useState(false);

  const handleBookmarkClick = async () => {
    setIsBookmarked(!isBookmarked);

    if (onBookmarkToggle) {
      onBookmarkToggle(!isBookmarked);
    }

//     try {
//       const response = await fetch(`http://localhost:8080/products/handleBookmark`, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ productId, userId, isBookmarked: !isBookmarked }),
//       });

//       if (!response.ok) {
//         throw new Error('Failed to update bookmark status');
//       }

//       console.log('Bookmark status updated successfully');
//       // If you have some state to update, use the appropriate setter here
//       // e.g., setTemp(false);
//     } catch (error) {
//       console.error('Error updating bookmark status:', error.message);
//       // Handle errors appropriately
//     }
//   };
try {
    const response = await fetch(`http://localhost:5000/posts`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId, userId, isBookmarked: !isBookmarked }),
    });

    if (!response.ok) {
      throw new Error('Failed to update bookmark status');
    }

    console.log('Bookmark status updated successfully');
    // If you have some state to update, use the appropriate setter here
    // e.g., setTemp(false);
  } catch (error) {
    console.error('Error updating bookmark status:', error.message);
    // Handle errors appropriately
  }
};

  return (
    <button
      className={`${styles.bookmarkButton} ${isBookmarked ? styles.bookmarked : styles.notbookmarked}`}
      onClick={handleBookmarkClick}
      title={`${isBookmarked ? 'remove' : 'add'} bookmark`}
    ></button>
  );
};

export default BookmarkButton;
