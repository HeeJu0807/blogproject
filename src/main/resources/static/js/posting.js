document.addEventListener('DOMContentLoaded', function () {
    const contentDiv = document.getElementById('content');
    const imageUpload = document.getElementById('image-upload');

    // 이미지 파일 선택 시
    imageUpload.addEventListener('change', function () {
        const files = this.files;
        if (files.length === 0) return;

        const file = files[0]; // Assuming only one file is selected
        const imageUrl = `/uploads/${file.name}`; // Replace with actual URL or path

        insertImageAtCursor(imageUrl);
    });

    // 커서 위치에 이미지 Markdown 포맷으로 삽입
    function insertImageAtCursor(url) {
        const markdownImage = `![Alt text](${url})`;

        // Insert image Markdown at current cursor position
        const { selectionStart, selectionEnd, value } = contentDiv;
        const newValue =
            value.substring(0, selectionStart) +
            markdownImage +
            value.substring(selectionEnd);

        // Update textarea value with the modified content
        contentDiv.value = newValue;

        // Restore focus and cursor position after updating content
        contentDiv.focus();
        const newCursorPosition = selectionStart + markdownImage.length;
        contentDiv.setSelectionRange(newCursorPosition, newCursorPosition);
    }
});
