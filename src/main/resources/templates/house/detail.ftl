<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-property-detail" id="page-top">
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
                <li class="active">House Detail</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <div class="row">
                <!-- Property Detail Content -->
                <div class="col-md-9 col-sm-9">
                    <section id="property-detail">
                        <header class="property-title">
                            <h1>${house.name}</h1>
                            <figure>${house.address}</figure>

                        <#if loginUser??>
                            <span class="actions">
                                <!--<a href="#" class="fa fa-print"></a>-->
                                <a href="#" class="bookmark" data-bookmark-state="empty"
 
                                ><span class="title-add">Add to bookmark</span><span class="title-added">Added</span></a>
                            </span>
                       </#if>
                        </header>
                        <section id="property-gallery">
                            <div class="owl-carousel property-carousel">
                                <#list house.imageList as image> 
                                   <div class="property-slide">
                                      <a href="${image}" class="image-popup">
                                         <div class="overlay"><h3>House Images</h3></div>
                                         <img alt="" src="${image}">
                                     </a>
                                   </div><!-- /.property-slide -->
                                </#list>
                            </div><!-- /.property-carousel -->
                        </section>
                        <div class="row">
                            <div class="col-md-4 col-sm-12">
                                <section id="quick-summary" class="clearfix">
                                    <header><h2>Summary</h2></header>
                                    <dl>
                                        <dt>Address</dt>
                                            <dd>${house.address}</dd>
                                        <dt>Price</dt>
                                            <dd><span class="tag price">$${house.price}</span></dd>
                                        <dt>Type:</dt>
                                            <dd>
                                              <#if house.type==1>For Sell<#else>For Rent</#if>
                                            </dd>
                                        <dt>Area:</dt>
                                            <dd>${house.area} sq.ft.</dd>
                                        <dt>Beds:</dt>
                                            <dd>${house.beds}</dd>
                                        <dt>Baths:</dt>
                                            <dd>${house.baths}</dd>
                                    </dl>
                                </section><!-- /#quick-summary -->
                            </div><!-- /.col-md-4 -->
                            <div class="col-md-8 col-sm-12">
                                <section id="description">
                                    <header><h2>Remarks</h2></header>
                                    ${house.remarks}
                                </section><!-- /#description -->
                                <section id="property-features">
                                    <header><h2>Features</h2></header>
                                    <ul class="list-unstyled property-features-list">
                                    <#list house.featureList as feature> 
                                        <li>${feature}</li>
                                    </#list>
                                    </ul>
                                </section><!-- /#property-features -->
                                <section id="floor-plans">
                                    <div class="floor-plans">
                                        <header><h2>Floor Plans</h2></header>
                                         <#list house.floorPlanList as floorPlan> 
                                            <a href="${floorPlan}" class="image-popup"><img alt="" src="${floorPlan}"></a>
                                         </#list>
                                    </div>
                                </section><!-- /#floor-plans -->
                            </div><!-- /.col-md-8 -->
                           
                            <div class="col-md-12 col-sm-12">
                                <#if agent?? >
                                   <section id="contact-agent">
                                    <header><h2 class="no-border">Listing Agent</h2></header>
                                     <div class="row">
                                       <div class="col-md-5 col-sm-12">
                                         <aside class="agent-info clearfix">
                                           <figure class="agency-image"><a href="/agency/agentDetail?id=${(agent.id)!}"><img alt="" src="${(agent.avatar)!}"></a></figure>
                                           <div class="agent-contact-info">
                                             <h3>${(agent.name)!}</h3>
                                             <p>
                                                 ${(agent.aboutMe)!}
                                             </p>
                                             <dl>
                                               <dt>Phone:</dt>
                                               <dd>${(agent.phone)!}</dd>
                                               <dt>Email:</dt>
                                               <dd><a href="mailto:#">${(agent.email)!}</a></dd>
                                               <dt>&nbsp;&nbsp;&nbsp;</dt>
                                               <dd>&nbsp;&nbsp;&nbsp;</dd>
                                             </dl>
                                             <hr>
                                           </div>
                                         </aside><!-- /.agent-info -->
                                       </div><!-- /.col-md-7 -->

                                       <div class="col-md-12 col-sm-12">
                                         <header><h2 class="no-border">Send Agent Message</h2></header>
                                     <div class="agent-form">
                                       <form role="form" id="form-contact-agent" method="post"  class="clearfix" action="/agency/agentMsg">
                                         <div class="row">
                                           <div class="col-md-6">
                                             <div class="form-group">
                                               <label for="form-contact-agent-name">Your Name<em>*</em></label>
                                               <input type="text" class="form-control" id="form-contact-agent-name"  name="userName" required>
                                             </div><!-- /.form-group -->
                                           </div><!-- /.col-md-6 -->
                                           <div class="col-md-6">
                                             <div class="form-group">
                                               <label for="form-contact-agent-email">Your Email<em>*</em></label>
                                               <input type="email"  class="form-control" id="form-contact-agent-email" name="userEmail" required>
                                             </div><!-- /.form-group -->
                                           </div><!-- /.col-md-6 -->
                                         </div><!-- /.row -->
                                         <input type="hidden" name="agentId" value="${agent.id!}">
                                         <div class="row">
                                           <div class="col-md-12">
                                             <div class="form-group">
                                               <label for="form-contact-agent-message">Your Message<em>*</em></label>
                                               <textarea class="form-control" id="form-contact-agent-message" rows="5" name="msg" required></textarea>
                                             </div><!-- /.form-group -->
                                           </div><!-- /.col-md-12 -->
                                         </div><!-- /.row -->
                                         <div class="form-group clearfix">
                                           <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">Send a Message</button>
                                         </div><!-- /.form-group -->
                                         <div id="form-rating-status"></div>
                                       </form><!-- /#form-contact -->
                                     </div><!-- /.agent-form -->
                                       </div>
                                     </div>
                                   </section><!-- /#contact-agent -->
                                </#if>
                               
                                <hr class="thick">
                                <section id="comments">
                                    <div class="agent-form">
                                                    <form role="form" id="form-contact-agent" method="post" action="/comment/addComment" class="clearfix">
                                                        <input type="hidden" name="houseId" value="${house.id}">
                                                        <div class="form-group">
                                                          <header><h2 class="no-border">Your Comment</h2></header>
                                                            <textarea class="form-control" id="form-contact-agent-message" rows="2" name="content" required></textarea>
                                                        </div><!-- /.form-group -->
                                                        <div class="form-group">
                                                            <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">Comment Now</button>
                                                        </div><!-- /.form-group -->
                                                        <div id="form-contact-agent-status"></div>
                                                    </form><!-- /#form-contact -->
                                                </div>
                                    <header><h2 class="no-border">Comments</h2></header>
                                    <ul class="comments">
                                      <#list commentList as comment> 
                                        <li class="comment" style="width: 830px;">
                                            <figure>
                                                <div class="image">
                                                    <img alt="" src="${comment.avatar}">
                                                </div>
                                            </figure>
                                            <div class="comment-wrapper">
                                                <div class="name pull-left">${comment.userName}</div>
                                                <span class="date pull-right"><span class="fa fa-calendar"></span>${(comment.createTime)?datetime}</span>
                                                <p>${comment.content}
                                                </p>
                                                <hr>
                                            </div>
                                        </li>
                                      </#list>
                                    </ul>
                                </section>
                            </div><!-- /.col-md-12 -->
                            
                        </div><!-- /.row -->
                    </section><!-- /#property-detail -->
                </div><!-- /.col-md-9 -->
                <!-- end Property Detail Content -->



              <!-- sidebar -->
              <div class="col-md-3 col-sm-3">
                <section id="sidebar">
                       <@common.search />
                       <@common.most_viewed_houses />
                       <@common.latest_houses />
                </section><!-- /#sidebar -->
              </div><!-- /.col-md-3 -->
              <!-- end Sidebar -->

            <@common.footer/>
            </div><!-- /.col-md-3 -->
          <!-- end Sidebar -->



        </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->

    <!-- end Page Footer -->
<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyABT1kCnk8CW4Ckpd0RisUg25PIdDD3Gfg"></script> -->

<@common.js/>

<!--[if gt IE 8]>
<script type="text/javascript" src="/static//js/ie.js"></script>
<![endif]-->
 <script  type="text/javascript" >
     
    
    
    $(window).load(function(){
        initializeOwl(false);
    });


    $(document).ready(function() {
      var errorMsg   = "${errorMsg!""}";
      var successMsg = "${successMsg!""}";
      if(errorMsg){
        errormsg("error",errorMsg);
      }
      if(successMsg) {
        successmsg("success",successMsg);
      }
    });


    var bookmarkButton = $(".bookmark");

    
    bookmarkButton.on("click", function() {
        if (bookmarkButton.data('bookmark-state') == 'empty') {
            commonAjax('/house/bookmark?id=${house.id}');
        } else if (bookmarkButton.data('bookmark-state') == 'added') {
            commonAjax('/house/unbookmark?id=${house.id}');
        }
    });
    
   

        
 </script>

</body>
</html>