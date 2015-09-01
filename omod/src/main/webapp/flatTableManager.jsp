<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp"%>

<h2>Flat Tables</h2>

<script type="text/javascript">

    $j(document).ready(function () {

        $j("#removeDuplicates").click(function (){

            DWRFacesCustomizationService.purgeSyncDuplicates(function(data){
               dwr.util.setValue("duplicatesResult", data);
            });

        });
    });

</script>


<b class="boxHeader">Flat Table Management</b>

<div class="box" style=" width:99%; height:auto;  overflow-x: auto;">
    <fieldset class="visualPadding">
        <legend>Level One Patient Data (Demographics and First Observations)</legend>
        <table>
            <tr>
                <td>Patient name, dob, sex, identifiers, Contacts</td>
                <td><input type="button" id="createDemo1" value="Create"> || <input type="button" id="updateDemo1" value="Update"></td>
            </tr>
            <tr>
                <td colspan="2"><div id="demo1Result"></div> </td>
            </tr>
            <tr>
                <td>Entry Point, first Obs (CD4, VL, Weight, Height, WHO Stage, Date Enrolled in care, ART Start Date)</td>
                <td><input type="button" id="createDemo2" value="Create"> || <input type="button" id="updateDemo2" value="Update"></td>
            </tr>
            <tr>
                <td colspan="2"><div id="demo2Result"></div> </td>
            </tr>
        </table>
    </fieldset>
    <fieldset class="visualPadding">
        <legend>Encounter Level HIV Data</legend>
        <table>
            <tr>
                <td>Observations (CD4, VL, Drug, Pregnancy, WHO Stage) in an encounter</td>
                <td><input type="button" id="createEncObs" value="Create"> || <input type="button" id="updateEncObs" value="Update"></td>
            </tr>
            <tr>
                <td colspan="2"><div id="encObsResult"></div> </td>
            </tr>

        </table>
    </fieldset>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
