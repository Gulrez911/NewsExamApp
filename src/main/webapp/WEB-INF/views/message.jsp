<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script>
</head>
<body>

<div class="container">
		<form action="sendMail2" method="post">
				<table>
						<tr>
								<td>To:</td>
								<td><input type="text" name="to" /></td>
						</tr>
						<tr>
								<td>Subject:</td>
								<td><input type="text" name="subject"></td>
						</tr>
						<tr>
								<td>Text:</td>
								<td><textarea id="summernote" name="text"></textarea></td>
						</tr>
						<tr>
								<td></td>
								<td><input type="submit" value="Send"></td>
						</tr>
				</table>
		</form>

</div>









</body>

<script>
	$('textarea#summernote').summernote(
			{
				placeholder : 'Hello bootstrap 4',
				tabsize : 2,
				height : 100,
				toolbar : [ [ 'style', [ 'style' ] ],
						[ 'font', [ 'bold', 'italic', 'underline', 'clear' ] ],
						// ['font', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
						//['fontname', ['fontname']],
						// ['fontsize', ['fontsize']],
						[ 'color', [ 'color' ] ],
						[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
						[ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
						[ 'insert', [ 'link', 'picture', 'hr' ] ],
						//['view', ['fullscreen', 'codeview']],
						[ 'help', [ 'help' ] ] ],
			});
</script>
</html>