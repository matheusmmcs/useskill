(function($){
	$(document).ready(function(){


		$('body').prepend('<div class="useskill-captelement useskill-hide"></div>');

		window.useskillHideCaptElement = function(){
			$('.useskill-captelement').hide();
		}

		window.useskillShowCaptElement = function(){
			$('.useskill-captelement').show();
		}

		$(document).on('mouseenter', '*', function(e){
			if(window.useskill_capt_element){
				removeClassUseSkillHover($('.useskill-element-hover'));
				$(e.target).addClass('useskill-element-hover');
			}
		});
		$(document).on('mouseleave', '*', function(e){
			if(window.useskill_capt_element){
				$(e.target).removeClass('useskill-element-hover');
			}
		});

		$(document).on('click', function(e){
			if(window.useskill_capt_element){
				e.preventDefault();
				e.stopPropagation();
				window.useskill_capt_element = false;

				var $e = $(e.target);

				if($e.closest('.useskill-captelement').length == 0){
					var count = 0, info = {
						action: $('input#action').val(),
						section: $('input#sectionName').val(),
						step: $('input#step').val(),
						elements: []
					};
					while($e && $e.prop('tagName') !== "BODY" && count < 3){
						//verify class, to remove attr if is empty
						removeClassUseSkillHover($e);
						info.elements.push({
							order: count,
							xpath: getElementXPath($e.get(0))//getElementXPath - createXPathFromElement
						});
						$e = $e.parent();
						count++;
					}

					showInfo(info);

					chrome.extension.sendRequest({useskillelement: "changeIcon"});
				}
			}
		});


		//Specific events
		//
		$(document).on('click', '#useskill-xpathelement > .useskill-elements-list-item', function(e){
			e.preventDefault();
			removeClassUseSkillHover($('.useskill-element-hover'));

			var xpath = $(this).find('span').html(), 
				el = getElementByXpath(xpath);
			
			console.log(xpath, el);

			$(el).addClass('useskill-element-hover');
		});

		$(document).on('click', '.useskill-elements-close', function(e){
			e.preventDefault();
			chrome.extension.sendRequest({useskillelement: "changeIconOff", close: true});
		});

		$(document).on('click', '.useskill-elements-clear', function(e){
			e.preventDefault();
			chrome.extension.sendRequest({useskillelement: "changeIconOff", close: true});
			removeClassUseSkillHover($('.useskill-element-hover'));
			$('.useskill-captelement').html("");
		});

		function removeClassUseSkillHover(elems){
			if(elems.length > 0){
				for(var i in elems){
					var $e = elems.eq(i);
					$e.removeClass('useskill-element-hover');
					if($e.attr('class') == ''){
						$e.removeAttr('class');
					}
				}
			}
		}

		function showInfo(info) {
			var div = '<div id="useskill-xpathelement" class="useskill-elements-list">';
			div += '<div class="useskill-elements-list-item useskill-elements-title">XPath Elements:</div>';
			for(var i in info.elements){
				var el = info.elements[i];
				div += '<div class="useskill-elements-list-item">['+i+'] - <span>'+el.xpath+'</span></div>';
			}
			div += '</div>';

			//right
			var btns = '<div class="useskill-elements-btns"><a class="useskill-elements-clear" href="#">Clear</a><a class="useskill-elements-close" href="#">Close</a></div>';

			div += '<div class="useskill-elements-list">';
			div += 	'<div class="useskill-elements-list-item useskill-elements-title">MetaData:'+btns+'</div>';
			div += 	'<div class="useskill-elements-list-item">[action] - '+info.action+'</div>';
			div += 	'<div class="useskill-elements-list-item">[section] - '+info.section+'</div>';
			div += 	'<div class="useskill-elements-list-item">[step] - '+info.step+'</div>';
			div += '</div>';
			
			$('.useskill-captelement').html(div);
		}

		function createXPathFromElement(elm) {
		    var allNodes = document.getElementsByTagName('*');
		    for (segs = []; elm && elm.nodeType == 1; elm = elm.parentNode){
		        if (elm.hasAttribute('id')) {
		                var uniqueIdCount = 0;
		                for (var n=0;n < allNodes.length;n++) {
		                    if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id) uniqueIdCount++;
		                    if (uniqueIdCount > 1) break;
		                };
		                if ( uniqueIdCount == 1) {
		                    segs.unshift('id("' + elm.getAttribute('id') + '")');
		                    return segs.join('/');
		                } else {
		                    segs.unshift(elm.localName.toLowerCase() + '[@id="' + elm.getAttribute('id') + '"]');
		                }
		        } else if (elm.hasAttribute('class')) {
		            segs.unshift(elm.localName.toLowerCase() + '[@class="' + elm.getAttribute('class') + '"]');
		        } else {
		            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
		                if (sib.localName == elm.localName)  i++; };
		                segs.unshift(elm.localName.toLowerCase() + '[' + i + ']');
		        };
		    };
		    return segs.length ? '/' + segs.join('/') : null;
		};

		/* other way to generate xpath */
		function getElementXPath(element){
		    if (element && element.id)
		        return '//*[@id="' + element.id + '"]';
		    else
		        return getElementTreeXPath(element);
		};

		function getElementTreeXPath(element){
		    var paths = [];

		    // Use nodeName (instead of localName) so namespace prefix is included (if any).
		    for (; element && element.nodeType == 1; element = element.parentNode)
		    {
		        var index = 0;
		        for (var sibling = element.previousSibling; sibling; sibling = sibling.previousSibling)
		        {
		            // Ignore document type declaration.
		            if (sibling.nodeType == Node.DOCUMENT_TYPE_NODE)
		                continue;

		            if (sibling.nodeName == element.nodeName)
		                ++index;
		        }

		        var tagName = element.nodeName.toLowerCase();
		        var pathIndex = (index ? "[" + (index+1) + "]" : "");
		        paths.splice(0, 0, tagName + pathIndex);
		    }

		    return paths.length ? "/" + paths.join("/") : null;
		};
		
		function getElementByXpath(path) {
			return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
		}

	});
})(jQuery);