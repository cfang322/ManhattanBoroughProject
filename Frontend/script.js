let samplePoints =[];
try {
  const saved = localStorage.getItem("points");
  samplePoints = saved ? JSON.parse(saved) : [];
} catch (e) {
  samplePoints = [];
}
  if (samplePoints.length === 0) {
    samplePoints = [
      {
    id: 1,
    name: "Wall Street",
    category: "Finance",
    description: "Historic center of American finance.",
    neighborhood: "Financial District",
    latitude: 40.7060,
    longitude: -74.0086
  },
  {
    id: 2,
    name: "Times Square",
    category: "Tourism",
    description: "Famous for bright lights, Broadway, shopping, and entertainment.",
    neighborhood: "Midtown",
    latitude: 40.7580,
    longitude: -73.9855
  },
  {
    id: 3,
    name: "The Metropolitan Museum of Art",
    category: "Arts",
    description: "One of the largest and most famous art museums in the world.",
    neighborhood: "Upper East Side",
    latitude: 40.7794,
    longitude: -73.9632
  },
  {
    id: 4,
    name: "Chinatown",
    category: "International Communities",
    description: "A historic neighborhood known for Chinese culture, restaurants, and shops.",
    neighborhood: "Lower Manhattan",
    latitude: 40.7158,
    longitude: -73.9970
  }
];
localStorage.setItem("points", JSON.stringify(samplePoints) );
  }

  //Reviews in memory (MVP)
const reviews = {};
//MAP
const map = L.map("map").setView([40.7831, -73.9712], 12);

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution: "© OpenStreetMap contributors"
}).addTo(map);

//CLICK IN MAPP
map.on("click", (e) => {
  const name = prompt("Name this place:");

  if (!name) return;

  const newPoint = {
    id: Date.now(),
    name: name,
    category: "User",
    description: "User created location",
    neighborhood: "",
    latitude: e.latlng.lat,
    longitude: e.latlng.lng
  };

  samplePoints.push(newPoint);

  localStorage.setItem("points", JSON.stringify(samplePoints));

  renderMap(samplePoints);

   L.popup()
   .setLatLng(e.latlng)
   .setContent("📍 Point added!")
   .openOn(map);
});


function openModal(point) {
  document.getElementById("modal").classList.remove("hidden");

  document.getElementById("pointName").textContent = point.name;
  document.getElementById("pointCategory").textContent = "Category: " + point.category;
  document.getElementById("pointDescription").textContent = point.description;
  document.getElementById("pointNeighborhood").textContent = "Neighborhood: " + point.neighborhood;
  document.getElementById("reviewsSection").dataset.pointId = point.id;

  if (!reviews[point.id]) {
    reviews[point.id] = [];
  }

  renderReviews(point.id);
}
//Marker
function addMarker(point) {
  const marker = L.marker([point.latitude, point.longitude]).addTo(map);

  marker.bindPopup(point.name);

  marker.on("click", () => {
    openModal(point);
  });
}
//RENDER MAPP
function renderMap(points) {
  map.eachLayer(layer => {
    if (layer instanceof L.Marker) {
      map.removeLayer(layer);
    }
  });

  points.forEach(addMarker);
}
//REVIEWSSS part
function renderReviews(pointId) {
  const list = document.getElementById("reviewsList");
  list.innerHTML = "";

  reviews[pointId].forEach((review, index) => {
    const card = document.createElement("div");
    card.classList.add("review-card");
    card.innerHTML = `
      <p>${review.text}</p>
      <div class="review-actions">
        <button class="like-btn" onclick="likeReview(${pointId}, ${index})">Like ${review.likes}</button>
        <button class="edit-btn" onclick="editReview(${pointId}, ${index})">Edit</button>
        <button class="delete-btn" onclick="deleteReview(${pointId}, ${index})">Delete</button>
      </div>
    `;
    list.appendChild(card);
  });
}

document.getElementById("submitReview").addEventListener("click", () => {
  const pointId = parseInt(document.getElementById("reviewsSection").dataset.pointId);
  const input = document.getElementById("reviewInput");
  const text = input.value.trim();

  if (!text) return;

  reviews[pointId].push({ text: text, likes: 0 });
  input.value = "";
  renderReviews(pointId);
});
//LIKE
function likeReview(pointId, index) {
  reviews[pointId][index].likes++;
  renderReviews(pointId);
}
//EDIT
function editReview(pointId, index) {
  const newText = prompt("Edit your review:", reviews[pointId][index].text);
  if (newText && newText.trim()) {
    reviews[pointId][index].text = newText.trim();
    renderReviews(pointId);
  }
}
//DELETE
function deleteReview(pointId, index) {
  reviews[pointId].splice(index, 1);
  renderReviews(pointId);

}
//CLOSE MODAL
document.getElementById("closeModal").addEventListener("click", () => {
  document.getElementById("modal").classList.add("hidden");
});
//INIT
renderMap(samplePoints);