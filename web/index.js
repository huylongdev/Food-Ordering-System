// 
//console.clear();

const loginBtn = document.getElementById('login');
const signupBtn = document.getElementById('signup');

loginBtn.addEventListener('click', (e) => {
	let parent = e.target.parentNode.parentNode;
	Array.from(e.target.parentNode.parentNode.classList).find((element) => {
		if(element !== "slide-up") {
			parent.classList.add('slide-up')
		}else{
			signupBtn.parentNode.classList.add('slide-up')
			parent.classList.remove('slide-up')
		}
	});
});

signupBtn.addEventListener('click', (e) => {
	let parent = e.target.parentNode;
	Array.from(e.target.parentNode.classList).find((element) => {
		if(element !== "slide-up") {
			parent.classList.add('slide-up')
		}else{
			loginBtn.parentNode.parentNode.classList.add('slide-up')
			parent.classList.remove('slide-up')
		}
	});
});





const userIcon = document.getElementById('user-icon');
const overlay = document.getElementById('update-avatar-overlay');

// Add a click event listener to the icon
userIcon.addEventListener('click', () => {
    console.log("click");
    // Toggle the overlay's visibility
    if (overlay.style.display === 'none' || overlay.style.display === '') {
        overlay.style.display = 'block'; // Show overlay
    } else {
        overlay.style.display = 'none'; // Hide overlay
    }
});




function showUpdateAvatarOverlay() {
    document.getElementById("update-avatar-overlay").style.display = "block";
}

function hideUpdateAvatarOverlay() {
    document.getElementById("update-avatar-overlay").style.display = "none";
}
