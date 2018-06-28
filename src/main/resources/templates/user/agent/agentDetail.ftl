<!DOCTYPE html>

<html lang="en-US">
 <@common.header/>

<body class="page-sub-page page-agent-detail" id="page-top">
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
                <li><a href="#">Agent</a></li>
                <li class="active">Agent Detail</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <div class="row">
                <!-- Agent Detail -->
                <div class="col-md-9 col-sm-9">
                    <section id="agent-detail">
                        <header><h1>${agent.name!}</h1></header>
                        <section id="agent-info">
                            <div class="row">
                                <div class="col-md-3 col-sm-3">
                                    <figure class="agent-image"><img alt="" src="${(agent.avatar)!}"></figure>
                                </div><!-- /.col-md-3 -->
                                <div class="col-md-5 col-sm-5">
                                    <h3>Contact</h3>
                                    <dl>
                                        <dt>Phone:</dt>
                                        <dd>${(agent.phone)!}</dd>
                                        <dt>Email:</dt>
                                        <dd><a href="mailto:#">${(agent.email)!}</a></dd>
                                        <dt>Agency:</dt>
                                        <dd>${(agent.agencyName)!}</dd>
                                    </dl>
                                </div><!-- /.col-md-5 -->
                                <div class="col-md-4 col-sm-4">
                                    <h3>About Me</h3>
                                    <p>${agent.aboutMe!}
                                    </p>
                                </div><!-- /.col-md-4 -->
                            </div><!-- /.row -->
                            
                        </section><!-- /#agent-info -->
                        <hr class="thick">
                        <section id="agent-properties">
                            <header><h3>My Listing Houses</h3></header>
                            <div class="layout-expandable">
                                <div class="row">
                                  <#list bindHouses as house>
                                    <div class="col-md-4 col-sm-4">
                                        <div class="property">
                                            <a href="/house/detail?id=${house.id}">
                                                <div class="property-image">
                                                    <img alt="" src="${house.firstImg}"/>
                                                </div>
                                                <div class="overlay">
                                                    <div class="info">
                                                        <div class="tag price">$${house.price}</div>
                                                        <h3>${house.name}</h3>
                                                        <figure>${house.address}</figure>
                                                    </div>
                                                    <ul class="additional-info">
                                                        <li>
                                                            <header>Area:</header>
                                                            <figure>${house.area} sq.ft.</figure>
                                                        </li>
                                                        <li>
                                                            <header>Beds:</header>
                                                            <figure>${house.beds}</figure>
                                                        </li>
                                                        <li>
                                                            <header>Baths:</header>
                                                            <figure>${house.baths}</figure>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </a>
                                        </div><!-- /.property -->
                                    </div><!-- /.col-md-4 -->
                                   </#list>
                                </div><!-- /.row-->
                               
                            </div>
                        </section><!-- /#agent-properties -->
                        <hr class="thick">

                        <div class="row">
                            <div class="">
                                <h3>Leave Me a Message</h3>
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
                        </div><!-- /.row -->
                    </section><!-- /#agent-detail -->

                  <hr class="thick">
                  <section id="comments">
                    <div class="agent-form">
                      <form role="form" id="form-contact-agent" method="post" action="/comment/addComment" class="clearfix">
                        <input type="hidden" name="agentId" value="${agent.id}">
                        <div class="form-group">
                          <label for="form-contact-agent-message">Your Comment</label>
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
                </div><!-- /.col-md-9 -->

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


        </div>
        </div>
                <!-- end Agent Detail -->



            </div><!-- /.row -->



    <!-- end Page Content -->
    <!-- Page Footer -->

    <!-- end Page Footer -->


<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="/assets/js/ie.js"></script>
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
        
 </script>

</body>
</html>