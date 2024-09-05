import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CommunityPage = () => {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const [newPost, setNewPost] = useState('');
  const [error, setError] = useState('');
  const [userName, setUserName] = useState('');
  const [userId, setUserId] = useState(localStorage.getItem('userId'));
  const token = localStorage.getItem('jwtToken');

  useEffect(() => {
    if (!userId || !token) {
      navigate('/authpage');
    } else {
      fetchPosts();
      fetchUserData();
    }
  }, [navigate, userId, token]);

  const fetchPosts = async () => {
    try {
      const response = await axios.get('http://localhost:8082/community/posts', {
        headers: { 'Authorization': `Bearer ${token}` },
      });
      setPosts(response.data);
    } catch (error) {
      console.error('Error fetching posts:', error);
      setError('Failed to fetch posts. Please try again.');
    }
  };

  const fetchUserData = async () => {
    try {
      const response = await axios.get(`http://localhost:6688/carbonTrack/profile/${userId}`, {
        headers: { 'Authorization': `Bearer ${token}` },
      });
      setUserName(response.data.name);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const handlePostChange = (e) => {
    setNewPost(e.target.value);
  };

  const handlePostSubmit = async (e) => {
    e.preventDefault();
    if (!newPost.trim()) {
      setError('Post content cannot be empty.');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8082/community/posts', {
        title: "Environment",
        content: newPost,
      }, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      setPosts([response.data.post, ...posts]);
      setNewPost('');
    } catch (error) {
      console.error('Error creating post:', error);
      setError('Failed to create post. Please try again.');
    }
  };

  return (
    <div className="bg-gray-100 min-h-screen">
      <header className="bg-gray-800 text-white p-4 flex justify-between items-center">
        <h1 className="text-xl font-bold">Community</h1>
        <nav className="space-x-6">
          <a href="/carbontracker" className="mx-4 hover:text-blue-500">Carbon Tracker</a>
          <a href="/community" className="mx-4 hover:text-blue-500">Community</a>
          <span className="mx-4">Welcome, {userName}</span>
          <button onClick={() => { localStorage.removeItem('userId'); localStorage.removeItem('jwtToken'); navigate('/authpage'); }} className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">Logout</button>
        </nav>
      </header>

      <main className="p-4">
        <div className="bg-white p-4 rounded shadow-lg max-w-4xl mx-auto">
          <h2 className="text-2xl font-semibold mb-4">Create a New Post</h2>
          {error && <p className="text-red-500 mb-4">{error}</p>}
          <form onSubmit={handlePostSubmit}>
            <textarea
              value={newPost}
              onChange={handlePostChange}
              className="w-full px-3 py-2 border rounded-lg mb-4"
              rows="4"
              placeholder="Write your post here..."
            />
            <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded-lg"
            >
              Post
            </button>
          </form>
        </div>

        <div className="mt-8">
          <h2 className="text-2xl font-semibold mb-4">Posts</h2>
          <div className="bg-white p-4 rounded shadow-lg max-w-4xl mx-auto">
            {posts.length > 0 ? (
              posts.map((post) => (
                <div key={post.id} className="border-b mb-4 pb-4">
                  <h3 className="text-lg font-semibold">{post.userName}</h3>
                  <p>{post.content}</p>
                </div>
              ))
            ) : (
              <p>No posts available.</p>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

export default CommunityPage;
