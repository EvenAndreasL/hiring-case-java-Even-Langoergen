
cli-build-prerequisites() {
    echo "Running sample command defined in cli-shortcuts.sh."
}

cli-resource-cli-shortcuts() {
    for f in ${WORKSPACE_FOLDER}/.devcontainer/cli-shortcuts*.sh; do source $f; done
}

# Restart and refresh app.
cli-docker-compose-app-refresh() {
    docker-compose stop
    for service in patgen-backend zipcode-service; do
        (cd "$WORKSPACE_FOLDER/$service" && mvn clean package -Dmaven.test.skip)
        (cd "$WORKSPACE_FOLDER" && docker-compose rm -f -v $service && docker-compose build $service)
    done
    (cd "$WORKSPACE_FOLDER" && docker-compose up -d)
}

cli-run-all-tests() {
    (cd ${WORKSPACE_FOLDER}/patgen-backend && mvn -B test) &&
    (cd ${WORKSPACE_FOLDER}/zipcode-service && mvn -B test) &&
    echo "Time to celebrate? 🎉"
}

cli-zip-assignment() {
    # Credit: https://remarkablemark.org/blog/2017/10/12/check-git-dirty/
    if [[ $(cd "$WORKSPACE_FOLDER" && git diff --stat) != '' ]]; then
        echo "Aborting zip-operation! Do you have files not committed to git yet? ⚠️"
        git status -s
    else
        local zipfile="$(basename "$WORKSPACE_FOLDER").zip"
        (
            cd "$WORKSPACE_FOLDER" && 
            rm -f "$zipfile" "${zipfile}.sha256sum" &&
            git archive -o "$zipfile" HEAD &&
            zip -r9 "$zipfile" .git &&
            sha256sum "$zipfile" > "$zipfile.sha256sum" &&
            sha256sum -c "$zipfile.sha256sum"
        )
        echo -e "\n👇\n"
        if [ "$CODESPACES" == "true" ]; then
            echo "Run the following command to download the assignment files via CLI:" &&
            echo
            echo "gh codespace cp remote:${WORKSPACE_FOLDER}/${zipfile}* ." &&
            echo
            echo "Or right click ${zipfile} and ${zipfile}.sha256sum in VSCode Explorer window and choose \"Download...\"."
        else
            echo "Assignment files can be found here:"
            ls -dl ${WORKSPACE_FOLDER}/${zipfile}*
        fi
    fi
}
