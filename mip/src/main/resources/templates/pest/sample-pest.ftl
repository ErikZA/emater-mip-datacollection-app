<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${pageTitle}</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/smart_wizard.css" type="text/css" />
    <link rel="stylesheet" href="css/smart_wizard_theme_arrows.css" type="text/css" />

</head>

<body>
    <div class="container-fluid">

        <form action="#" method="post" class="card" style="margin: 15px">
            <div class="card-header text-white" style="background-color: #004900">
                <h2>${pageTitle}</h2>
            </div>

            <div id="smartwizard" class="card-body">

                <ul>
                    <li>
                        <a href="#sample-data-tab">Dados da Amostragem</a>
                    </li>
                    <li>
                        <a href="#pest-table-tab">Flutuação Populacional</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="sample-data-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            Dados da Amostragem
                        </div>
                        <div class="card-body">
                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="sampleDate">Data da Coleta</label>
                                    <input type="date" class="form-control" id="sampleDate" name="sampleDate">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="growthPhase">Estádio da Cultura</label>
                                    <select class="form-control" id="growthPhase" name="growthPhase">
                                        <option value="harvest.id">harvest.name</option>
                                        <option value="harvest.id">harvest.name</option>
                                        <option value="harvest.id">harvest.name</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                   <label for="daysAfterEmergence">Dias Após Emergência</label>
                                    <input type="number" class="form-control" id="daysAfterEmergence" name="daysAfterEmergence">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col">
                                    <label for="defoliation">% de Desfolha (em números inteiros)</label>
                                    <input type="number" class="form-control" id="defoliation" name="defoliation">
                                </div>
                            </div>
                                
                        </div>
                    </div>

                    <div id="pest-table-tab" class="card" style="margin-top: 15px">
                        <div class="card-header text-white" style="background-color: #004900">
                            Flutuação Populacional dos Insetos Pragas
                        </div>
                        <div class="card-body table-responsive-md">
                            <table id="mainTable" class="table table-striped table-hover">
                                <thead style="background-color: #004900; color: white">
                                    <tr>
                                        <th>Insetos Praga</th>
                                        <th>Média Encontrada</th>
                                    </tr>
                                </thead>
                                <tbody id="mainTable-body">
                                    <tr>
                                        <td>Lagarta da Soja - Anticarsia gemmatalis (
                                            < 1,5 cm)</td> <td><input class="form-control" id="search" type="text"
                                                    placeholder="0.0"></td>
                                    </tr>
                                    <tr>
                                        <td>Falsa Medideira - Chrysodeixis spp. (> 1,5 cm)</td>
                                        <td><input class="form-control" id="search" type="text" placeholder="0.0"></td>
                                    </tr>
                                    <tr>
                                        <td>Grupo Heliothinae (
                                            < 1,5 cm)</td> <td><input class="form-control" id="search" type="text"
                                                    placeholder="0.0"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </div>

    <!-- External JS libs -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="js/jquery.smartWizard.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            // Smart Wizard
            $('#smartwizard').smartWizard({
                lang: {  // Language variables
                    next: 'Próximo',
                    previous: 'Anterior'
                },
                theme: 'arrows',
                transitionEffect: 'fade', // Effect on navigation, none/slide/fade
                transitionSpeed: '400'
            });

        }); 
    </script>
</body>

</html>