
var firebaseConfig = {
    apiKey: "AIzaSyBSksBzXGICDh6T___VqeYJxRGyzxezZdM",
    authDomain: "akillireklamyonetimsistemi.firebaseapp.com",
    databaseURL: "https://akillireklamyonetimsistemi.firebaseio.com",
    projectId: "akillireklamyonetimsistemi",
    storageBucket: "akillireklamyonetimsistemi.appspot.com",
    messagingSenderId: "436465128781",
    appId: "1:436465128781:web:d483db31cdfb5273"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

//reference messages collection
var messagesRef = firebase.database().ref('Reklam');

//listen for form submit
document.getElementById('contactForm').addEventListener('submit',submitForm);

function submitForm(e){
    e.preventDefault();
    
    //get values
    var name = getInputVal('name');
    var locationLatitude = getInputVal('locationLatitude');
    var locationLangitude = getInputVal('locationLangitude');
    var time = getInputVal('time');
    var kategori = getInputVal('kategori');
    var details = getInputVal('details');

    saveMessage(name,locationLatitude,locationLangitude,time,kategori,details);
   
}

//Function to get get form values
function getInputVal(id){
    return document.getElementById(id).value;
}

//save message to firebase
function saveMessage(name,locationLatitude,locationLangitude,time,kategori,details){
    
    var newMessageRef = messagesRef.push();
    newMessageRef.set({
        name: name,
        locationLatitude:locationLatitude,
        locationLangitude:locationLangitude,
        time:time,
        kategori:kategori,
        details:details
    });
}