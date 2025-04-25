$(document).ready(function () {

               
               const timelineItems = $('.timeline-item');
               let animatedCount = 0;

               timelineItems.css(
                   {'opacity': 0, 'transform': 'translateX(-50px)', 'transition': 'opacity 0.6s ease-out, transform 0.6s ease-out'}
               );

               if ($(window).width() > 768) {
                   timelineItems
                       .filter(':nth-child(even)')
                       .css({'transform': 'translateX(50px)'});
               }

               $(".timeline-content").hover(function () {
                   $(this)
                       .prevAll(".timeline-connector")
                       .first()
                       .addClass("hover-force");
                   $(this)
                       .prevAll(".timeline-connector")
                       .first()
                       .addClass("hover-force");
               }, function () {
                   $(this)
                       .prevAll(".timeline-connector")
                       .first()
                       .removeClass("hover-force");
                   $(this)
                       .prevAll(".timeline-connector")
                       .first()
                       .removeClass("hover-force");
               });
               $(".timeline-connector").hover(function () {
                   $(this)
                       .nextAll(".timeline-content")
                       .first()
                       .addClass("hover-force-content");
                   $(this)
                       .prevAll(".timeline-content")
                       .first()
                       .addClass("hover-force-content");
               }, function () {
                   $(this)
                       .nextAll(".timeline-content")
                       .first()
                       .removeClass("hover-force-content");
                   $(this)
                       .prevAll(".timeline-content")
                       .first()
                       .removeClass("hover-force-content");
               });

               const $timeline = $('.timeline');
               let timelineAnimated = false;

               $(window).on('scroll', function () {

                   if (animatedCount >= timelineItems.length) {
                       return;
                   }

                   const windowBottom = $(window).scrollTop() + $(window).height();

                   timelineItems.each(function (index) {
                       const itemTop = $(this)
                           .offset()
                           .top;

                       if ($(this).data('animated')) {
                           return;
                       }

                       if (windowBottom > itemTop + 100) {
                           const $item = $(this);

                           if ($(window).width() <= 768) {
                               $item.css({'opacity': 1, 'transform': 'translateX(0)'});
                           } else {

                               const isOdd = index % 2 === 0;
                               const translateX = isOdd
                                   ? '0'
                                   : '0';
                               $item.css({'opacity': 1, 'transform': `translateX(${translateX})`});
                           }

                           $item.data('animated', true);
                           animatedCount++;

                       }
                   });

                   if (!timelineAnimated) {
                       const timelineTop = $timeline
                           .offset()
                           .top;

                       if (windowBottom > timelineTop + 100) {
                           $timeline.addClass('animated');
                           timelineAnimated = true;
                       }
                   }
               });

               $(window).trigger('scroll');

           });