	
	//==================================
	//USER PAGE DASHBOARD
	//==================================
	const sideLinks = document.querySelector("#sidebar-links");
	const sidebar = document.querySelector("#sidebar");
	const contetnBox = document.querySelector("#content-box");
	
	window.addEventListener('resize', function (event) {
		
	});

	function toggleFunction(elem) {
		elem.classList.toggle('active');
		sideLinks.classList.toggle('active');
		sidebar.classList.toggle('active');
		contetnBox.classList.toggle('active');
	}
	
	//==================================
	//USER PAGE DASHBOARD ENDS
	//=================================
	
	
	
	function contactPage(link){
		window.location.href=link;
	}
	
	
	function deleteConfirmation(element, event, conId){
			
		event.preventDefault();
						
		Swal.fire({			
		  title: 'Are you sure?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.isConfirmed) {	

			//	request send kortesi
		    $.ajax({
			  type: "POST",
			  url: "/user/delete-contact",
			  data: "contact_id="+conId,
			  success:  function(result) {
	            Swal.fire({
					  icon: 'success',
					  title: 'Deleted',
					  text: 'Contact Deleted!'					  
					}).then((result) =>{
						window.location.href="/user/view-contacts/page/1"						
					})
			  }			  
			});
		    
		  }
		})		

		return false;
		
	}
	
	
	
	
	//search bar er jonno
	var searchBar = document.getElementById("searchContact");
	var resultBox = document.querySelector("#search-result-box");
	function searchContact(){
		var query = searchBar.value
		
		//console.log(query)
		if(query == ""){
			searchBar.style.width = "40%";

			resultBox.innerHTML = "";
			resultBox.style.display = "none";
		}else{
			searchBar.style.width = "100%";
			
						
			let url = `http://localhost:5595/search/${query}`;
			fetch(url).then((response)=>{
				return response.json();
			}).then((data)=>{
				//console.log(data);
				
				var text = "";
				data.forEach(element => {
					
					text += "<li>";
					text += `
						<a href="${'/user/contact/'+element.con_id}">
							<img src="${'/assets/img/contactImage/'+element.image}" alt="${''}">
							<span>${element.name}</span>
						</a>`
					text += "</li>"
					//console.log(element);

					
					//console.log("Working");
					
				});
				
				resultBox.style.display = "initial";
				resultBox.innerHTML = text;
				
								
			})
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	