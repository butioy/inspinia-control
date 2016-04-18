<%--
  Created by IntelliJ IDEA.
  User: butioy
  Date: 2016/3/30
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element"> <span>
                            <img alt="image" class="img-circle" src="img/profile_small.jpg"/>
                             </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                            <span class="clear"> <span class="block m-t-xs">
                                <strong class="font-bold">${USER_INFO_MAP_KEY.fullName}</strong>
                             </span> <span class="text-muted text-xs block">Art Director <b
                                    class="caret"></b></span> </span> </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a class="J_menuItem" href="/pages/profile.html">modify head img</a></li>
                        <li><a class="J_menuItem" href="/pages/profile.html">Profile</a></li>
                        <li><a class="J_menuItem" href="/pages/contacts.html">Contacts</a></li>
                        <li><a class="J_menuItem" href="/pages/mailbox.html">Mailbox</a></li>
                        <li class="divider"></li>
                        <li><a href="login.html">Logout</a></li>
                    </ul>
                </div>
                <div class="logo-element">Butioy
                </div>
            </li>
            <li class="active">
                <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">Dashboards</span> <span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li class="active"><a class="J_menuItem" href="/pages/index.html">Dashboard v.1</a></li>
                    <li><a class="J_menuItem" href="/pages/dashboard_2.html">Dashboard v.2</a></li>
                    <li><a class="J_menuItem" href="/pages/dashboard_3.html">Dashboard v.3</a></li>
                    <li><a class="J_menuItem" href="/pages/dashboard_4_1.html">Dashboard v.4</a></li>
                    <li><a class="J_menuItem" href="/pages/dashboard_5.html">Dashboard v.5 <span
                            class="label label-primary pull-right">NEW</span></a></li>
                </ul>
            </li>
            <li>
                <a class="J_menuItem" href="/pages/layouts.html"><i class="fa fa-diamond"></i> <span class="nav-label">Layouts</span></a>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">Graphs</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/graph_flot.html">Flot Charts</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_morris.html">Morris.js Charts</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_rickshaw.html">Rickshaw Charts</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_chartjs.html">Chart.js</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_chartist.html">Chartist</a></li>
                    <li><a class="J_menuItem" href="/pages/c3.html">c3 charts</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_peity.html">Peity Charts</a></li>
                    <li><a class="J_menuItem" href="/pages/graph_sparkline.html">Sparkline Charts</a></li>
                </ul>
            </li>
            <li>
                <a href="mailbox.html"><i class="fa fa-envelope"></i> <span class="nav-label">Mailbox </span><span
                        class="label label-warning pull-right">16/24</span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/mailbox.html">Inbox</a></li>
                    <li><a class="J_menuItem" href="/pages/mail_detail.html">Email view</a></li>
                    <li><a class="J_menuItem" href="/pages/mail_compose.html">Compose email</a></li>
                    <li><a class="J_menuItem" href="/pages/email_template.html">Email templates</a></li>
                </ul>
            </li>
            <li>
                <a class="J_menuItem" href="/pages/metrics.html"><i class="fa fa-pie-chart"></i> <span class="nav-label">Metrics</span> </a>
            </li>
            <li>
                <a class="J_menuItem" href="/pages/widgets.html"><i class="fa fa-flask"></i> <span class="nav-label">Widgets</span></a>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-edit"></i> <span class="nav-label">Forms</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/form_basic.html">Basic form</a></li>
                    <li><a class="J_menuItem" href="/pages/form_advanced.html">Advanced Plugins</a></li>
                    <li><a class="J_menuItem" href="/pages/form_wizard.html">Wizard</a></li>
                    <li><a class="J_menuItem" href="/pages/form_file_upload.html">File Upload</a></li>
                    <li><a class="J_menuItem" href="/pages/form_editors.html">Text Editor</a></li>
                    <li><a class="J_menuItem" href="/pages/form_markdown.html">Markdown</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-desktop"></i> <span class="nav-label">App Views</span> <span
                        class="pull-right label label-primary">SPECIAL</span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/contacts.html">Contacts</a></li>
                    <li><a class="J_menuItem" href="/pages/profile.html">Profile</a></li>
                    <li><a class="J_menuItem" href="/pages/profile_2.html">Profile v.2</a></li>
                    <li><a class="J_menuItem" href="/pages/contacts_2.html">Contacts v.2</a></li>
                    <li><a class="J_menuItem" href="/pages/projects.html">Projects</a></li>
                    <li><a class="J_menuItem" href="/pages/project_detail.html">Project detail</a></li>
                    <li><a class="J_menuItem" href="/pages/teams_board.html">Teams board</a></li>
                    <li><a class="J_menuItem" href="/pages/social_feed.html">Social feed</a></li>
                    <li><a class="J_menuItem" href="/pages/clients.html">Clients</a></li>
                    <li><a class="J_menuItem" href="/pages/full_height.html">Outlook view</a></li>
                    <li><a class="J_menuItem" href="/pages/vote_list.html">Vote list</a></li>
                    <li><a class="J_menuItem" href="/pages/file_manager.html">File manager</a></li>
                    <li><a class="J_menuItem" href="/pages/calendar.html">Calendar</a></li>
                    <li><a class="J_menuItem" href="/pages/issue_tracker.html">Issue tracker</a></li>
                    <li><a class="J_menuItem" href="/pages/blog.html">Blog</a></li>
                    <li><a class="J_menuItem" href="/pages/article.html">Article</a></li>
                    <li><a class="J_menuItem" href="/pages/faq.html">FAQ</a></li>
                    <li><a class="J_menuItem" href="/pages/timeline.html">Timeline</a></li>
                    <li><a class="J_menuItem" href="/pages/pin_board.html">Pin board</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-files-o"></i> <span class="nav-label">Other Pages</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/search_results.html">Search results</a></li>
                    <li><a class="J_menuItem" href="/pages/lockscreen.html">Lockscreen</a></li>
                    <li><a class="J_menuItem" href="/pages/invoice.html">Invoice</a></li>
                    <li><a class="J_menuItem" href="/pages/login.html">Login</a></li>
                    <li><a class="J_menuItem" href="/pages/login_two_columns.html">Login v.2</a></li>
                    <li><a class="J_menuItem" href="/pages/forgot_password.html">Forget password</a></li>
                    <li><a class="J_menuItem" href="/pages/register.html">Register</a></li>
                    <li><a class="J_menuItem" href="/pages/404.html">404 Page</a></li>
                    <li><a class="J_menuItem" href="/pages/500.html">500 Page</a></li>
                    <li><a class="J_menuItem" href="/pages/empty_page.html">Empty page</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-globe"></i> <span class="nav-label">Miscellaneous</span><span
                        class="label label-info pull-right">NEW</span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/toastr_notifications.html">Notification</a></li>
                    <li><a class="J_menuItem" href="/pages/nestable_list.html">Nestable list</a></li>
                    <li><a class="J_menuItem" href="/pages/agile_board.html">Agile board</a></li>
                    <li><a class="J_menuItem" href="/pages/timeline_2.html">Timeline v.2</a></li>
                    <li><a class="J_menuItem" href="/pages/diff.html">Diff</a></li>
                    <li><a class="J_menuItem" href="/pages/i18support.html">i18 support</a></li>
                    <li><a class="J_menuItem" href="/pages/sweetalert.html">Sweet alert</a></li>
                    <li><a class="J_menuItem" href="/pages/idle_timer.html">Idle timer</a></li>
                    <li><a class="J_menuItem" href="/pages/truncate.html">Truncate</a></li>
                    <li><a class="J_menuItem" href="/pages/spinners.html">Spinners</a></li>
                    <li><a class="J_menuItem" href="/pages/tinycon.html">Live favicon</a></li>
                    <li><a class="J_menuItem" href="/pages/google_maps.html">Google maps</a></li>
                    <li><a class="J_menuItem" href="/pages/code_editor.html">Code editor</a></li>
                    <li><a class="J_menuItem" href="/pages/modal_window.html">Modal window</a></li>
                    <li><a class="J_menuItem" href="/pages/clipboard.html">Clipboard</a></li>
                    <li><a class="J_menuItem" href="/pages/forum_main.html">Forum view</a></li>
                    <li><a class="J_menuItem" href="/pages/validation.html">Validation</a></li>
                    <li><a class="J_menuItem" href="/pages/tree_view.html">Tree view</a></li>
                    <li><a class="J_menuItem" href="/pages/loading_buttons.html">Loading buttons</a></li>
                    <li><a class="J_menuItem" href="/pages/chat_view.html">Chat view</a></li>
                    <li><a class="J_menuItem" href="/pages/masonry.html">Masonry</a></li>
                    <li><a class="J_menuItem" href="/pages/tour.html">Tour</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-flask"></i> <span class="nav-label">UI Elements</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/typography.html">Typography</a></li>
                    <li><a class="J_menuItem" href="/pages/icons.html">Icons</a></li>
                    <li><a class="J_menuItem" href="/pages/draggable_panels.html">Draggable Panels</a></li>
                    <li><a class="J_menuItem" href="/pages/resizeable_panels.html">Resizeable Panels</a></li>
                    <li><a class="J_menuItem" href="/pages/buttons.html">Buttons</a></li>
                    <li><a class="J_menuItem" href="/pages/video.html">Video</a></li>
                    <li><a class="J_menuItem" href="/pages/tabs_panels.html">Panels</a></li>
                    <li><a class="J_menuItem" href="/pages/tabs.html">Tabs</a></li>
                    <li><a class="J_menuItem" href="/pages/notifications.html">Notifications & Tooltips</a></li>
                    <li><a class="J_menuItem" href="/pages/badges_labels.html">Badges, Labels, Progress</a></li>
                </ul>
            </li>

            <li>
                <a class="J_menuItem" href="/pages/grid_options.html"><i class="fa fa-laptop"></i> <span class="nav-label">Grid options</span></a>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-table"></i> <span class="nav-label">Tables</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/table_basic.html">Static Tables</a></li>
                    <li><a class="J_menuItem" href="/pages/table_data_tables.html">Data Tables</a></li>
                    <li><a class="J_menuItem" href="/pages/table_foo_table.html">Foo Tables</a></li>
                    <li><a class="J_menuItem" href="/pages/jq_grid.html">jqGrid</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-shopping-cart"></i> <span
                        class="nav-label">E-commerce</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/ecommerce_products_grid.html">Products grid</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce_product_list.html">Products list</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce_product.html">Product edit</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce_product_detail.html">Product detail</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce-cart.html">Cart</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce-orders.html">Orders</a></li>
                    <li><a class="J_menuItem" href="/pages/ecommerce_payments.html">Credit Card form</a></li>
                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-picture-o"></i> <span class="nav-label">Gallery</span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li><a class="J_menuItem" href="/pages/basic_gallery.html">Lightbox Gallery</a></li>
                    <li><a class="J_menuItem" href="/pages/slick_carousel.html">Slick Carousel</a></li>
                    <li><a class="J_menuItem" href="/pages/carousel.html">Bootstrap Carousel</a></li>

                </ul>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-sitemap"></i> <span class="nav-label">Menu Levels </span><span
                        class="fa arrow"></span></a>
                <ul class="nav nav-second-level collapse">
                    <li>
                        <a href="index.html#">Third Level <span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level">
                            <li>
                                <a href="index.html#">Third Level Item</a>
                            </li>
                            <li>
                                <a href="index.html#">Third Level Item</a>
                            </li>
                            <li>
                                <a href="index.html#">Third Level Item</a>
                            </li>

                        </ul>
                    </li>
                    <li><a href="index.html#">Second Level Item</a></li>
                    <li>
                        <a href="index.html#">Second Level Item</a></li>
                    <li>
                        <a href="index.html#">Second Level Item</a></li>
                </ul>
            </li>
            <li>
                <a class="J_menuItem" href="/pages/css_animation.html"><i class="fa fa-magic"></i> <span
                        class="nav-label">CSS Animations </span><span class="label label-info pull-right">62</span></a>
            </li>
            <li class="landing_link">
                <a class="J_menuItem" href="/pages/landing.html"><i class="fa fa-star"></i> <span class="nav-label">Landing Page</span>
                    <span class="label label-warning pull-right">NEW</span></a>
            </li>
            <li class="special_link">
                <a class="J_menuItem" href="/pages/package.html"><i class="fa fa-database"></i> <span class="nav-label">Package</span></a>
            </li>
        </ul>

    </div>
</nav>