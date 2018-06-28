<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-listing-lines page-search-results" id="page-top">
<!-- Wrapper -->
<div class="wrapper">
    <!-- Navigation -->
   <@common.nav/><!-- /.navigation -->
    <!-- end Navigation -->
    <!-- Page Content -->
    <div id="page-content">
        <!-- Breadcrumb -->
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li class="active">House List</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <div class="row">
                <!-- Results -->
                <div class="col-md-9 col-sm-9">
                    <section id="results">
                        <header><h1>House List</h1></header>
                        <section id="search-filter">
                            <figure><h3><i class="fa fa-search"></i>Search Results:</h3>
                                <span class="search-count"></span>
                                 <div class="sorting">
                                    <div class="form-group">
                                        <select name="sorting" id="sorting">
                                        <option value="">Sort By</option>
                                        <option value="price_asc"   <#if (vo.sort) == "price_asc">   selected </#if>  >Price Low to High</option>
                                        <option value="price_desc"  <#if (vo.sort) == "price_desc">  selected </#if> >Price High to Low</option>
                                            <option value="time_desc"   <#if (vo.sort) == "time_desc">   selected </#if> >Most Recent</option>
                                        </select>
                                    </div><!-- /.form-group -->
                                </div>
                            </figure>
                        </section>
                    <section id="properties" class="display-lines">
                      <#list ps.list as house> 
                       
                            <div class="property">
                                <figure class="tag status">${house.typeStr}</figure>
                                <div class="property-image">
                                    <figure class="ribbon">In Hold</figure>
                                    <a href="/house/detail?id=${house.id}">
                                        <img alt="No Image" src="${house.firstImg}" style="width: 260px;height: 195px" >
                                    </a>
                                </div>

                                <div class="info">
                                    <header>
                                        <a href="/house/detail?id=${house.id}"><h3>${house.name}</h3></a>
                                        <figure>${house.address}</figure>

                                    </header>
                                    <div class="tag price">$${house.price}</div>
                                    <aside>
                                      <p>${house.remarks}
                                      </p>

                                      <dl>
                                        <dt style="float: left;clear: left;margin-right: 5px;">Status:</dt>
                                            <dd style="margin-left: 0px;">${house.typeStr}</dd>
                                        <dt style="float: left;clear: left;margin-right: 5px;">Area:</dt>
                                            <dd style="margin-left: 0px;">${house.area} sq.ft.</dd>
                                        <dt style="float: left;clear: left;margin-right: 5px;">Beds:</dt>
                                            <dd style="margin-left: 0px;">${house.beds}</dd>
                                        <dt style="float: left;clear: left;margin-right: 5px;">Baths:</dt>
                                            <dd style="margin-left: 0px;">${house.baths}</dd>
                                      </dl>
                                    </aside>


                                    <a href="/house/detail?id=${house.id}" class="link-arrow">Read More</a>
                                </div>
                            </div>
                        </#list>
                       </section>
                            <!-- Pagination -->
                            <div class="center">
                                 <@common.paging ps.pagination/>
                            </div><!-- /.center-->
                        </section><!-- /#properties-->
                    </section><!-- /#results -->
                </div><!-- /.col-md-9 -->
                <!-- end Results -->
              <!-- sidebar -->
              <div class="col-md-3 col-sm-3">
                <section id="sidebar">
                       <@common.search />
                       <@common.most_viewed_houses />
                       <@common.latest_houses />
                </section><!-- /#sidebar -->
              </div><!-- /.col-md-3 -->
              <!-- end Sidebar -->
            </div><!-- /.col-md-3 -->
          <!-- end Sidebar -->


          <@common.footer/>
            </div><!-- /.row -->
        </div><!-- /.container -->

    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->

    <!-- end Page Footer -->

<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="assets/js/ie.js"></script>
<![endif]-->
 <script  type="text/javascript" >
     

     $(document).ready(function() {
          var errorMsg   = "${errorMsg!""}";
          var successMsg = "${successMsg!""}";
          if(errorMsg){ 
              errormsg("error",errorMsg);
          }
          if(successMsg) {
              successmsg("success",successMsg);
          }
        })
      
      
  
     
      $('#sorting').change(function() {
           var sort =  $(this).val();
           if (!sort) {
               return;
           }
           window.location.href=  "/house/list?sort=" + sort + "&name=" + "${(vo.name)!}" + "&type=" + "${(vo.type)!0}" ;
       });

        
 </script>

</body>
</html>