<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp"%>

<h2>Sync Status</h2>

<script type="text/javascript">

    $j(document).ready(function () {

        $j("#removeDuplicates").click(function (){

            DWRFacesCustomizationService.purgeSyncDuplicates(function(data){
               dwr.util.setValue("duplicatesResult", data);
            });

        });
    });

</script>


<b class="boxHeader">Sync Process Status</b>

<div class="box" style=" width:99%; height:auto;  overflow-x: auto;">
    <fieldset class="visualPadding">
        <table>
            <tr>
                <td>Remove Duplicates</td>
                <td><input type="button" id="removeDuplicates" value="Remove Duplicates"></td>
            </tr>
            <tr>
                <td colspan="2"><div id="duplicatesResult"></div> </td>
            </tr>
            <tr>
                <td>Sync Status</td>
                <td><input type="button" id="maintenance" value="View Sync Status"></td>
            </tr>
            <tr>
                <td colspan="2"><div id="syncResult"></div> </td>
            </tr>
        </table>


    </fieldset>

</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
